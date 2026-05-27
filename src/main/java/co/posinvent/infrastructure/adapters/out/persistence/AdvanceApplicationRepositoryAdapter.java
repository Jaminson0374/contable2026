package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.AdvanceApplication;
import co.posinvent.domain.repository.AdvanceApplicationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
class AdvanceApplicationRepositoryAdapter implements AdvanceApplicationRepository {

    private final AdvanceApplicationJpaRepository jpa;
    private final AdvanceApplicationMapper mapper;

    AdvanceApplicationRepositoryAdapter(
            AdvanceApplicationJpaRepository jpa,
            AdvanceApplicationMapper mapper
    ) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public AdvanceApplication save(AdvanceApplication application) {
        var entity = mapper.toEntity(application);
        var saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<AdvanceApplication> findByAdvancePaymentId(UUID advancePaymentId) {
        return jpa.findByAdvancePaymentId(advancePaymentId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
