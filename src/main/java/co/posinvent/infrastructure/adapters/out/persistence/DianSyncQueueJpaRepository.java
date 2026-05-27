package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DianSyncQueueJpaRepository extends JpaRepository<DianSyncQueueItemEntity, UUID> {

    List<DianSyncQueueItemEntity> findByStatusAndNextAttemptAtBefore(SyncStatus status, OffsetDateTime now);

    Optional<DianSyncQueueItemEntity> findByElectronicInvoiceId(UUID invoiceId);
}
