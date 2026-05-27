package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.AuditLog;
import co.posinvent.domain.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
class AuditLogRepositoryAdapter implements AuditLogRepository {

    private final AuditLogJpaRepository jpa;
    private final AuditLogMapper mapper;

    AuditLogRepositoryAdapter(AuditLogJpaRepository jpa, AuditLogMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public AuditLog save(AuditLog auditLog) {
        var entity = mapper.toEntity(auditLog);
        entity.setId(UUID.randomUUID());
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Page<AuditLog> findFiltered(
            String entityType, UUID userId, String action,
            OffsetDateTime from, OffsetDateTime to, Pageable pageable) {

        boolean hasEntity = entityType != null;
        boolean hasUser = userId != null;
        boolean hasAction = action != null;
        boolean hasDates = from != null && to != null;

        Page<AuditLogEntity> result;

        if (hasEntity && hasUser && hasAction && hasDates) {
            result = jpa.findByEntityTypeAndUserIdAndActionAndCreatedAtBetweenOrderByCreatedAtDesc(
                    entityType, userId, action, from, to, pageable);
        } else if (hasEntity && hasUser && hasDates) {
            result = jpa.findByEntityTypeAndUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                    entityType, userId, from, to, pageable);
        } else if (hasEntity && hasDates) {
            result = jpa.findByEntityTypeAndCreatedAtBetweenOrderByCreatedAtDesc(
                    entityType, from, to, pageable);
        } else if (hasDates) {
            result = jpa.findByCreatedAtBetweenOrderByCreatedAtDesc(from, to, pageable);
        } else if (hasEntity) {
            result = jpa.findByEntityTypeOrderByCreatedAtDesc(entityType, pageable);
        } else {
            result = jpa.findAllByOrderByCreatedAtDesc(pageable);
        }

        return result.map(mapper::toDomain);
    }
}
