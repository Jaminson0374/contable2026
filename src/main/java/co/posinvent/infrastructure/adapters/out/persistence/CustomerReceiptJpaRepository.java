package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CustomerReceiptJpaRepository extends JpaRepository<CustomerReceiptEntity, UUID> {

    Page<CustomerReceiptEntity> findByClientId(UUID clientId, Pageable pageable);
}
