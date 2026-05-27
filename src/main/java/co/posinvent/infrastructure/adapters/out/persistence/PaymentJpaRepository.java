package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {

    Page<PaymentEntity> findBySupplierId(UUID supplierId, Pageable pageable);

    Page<PaymentEntity> findByIsAdvanceTrue(Pageable pageable);

    Page<PaymentEntity> findByIsAdvanceTrueAndSupplierId(UUID supplierId, Pageable pageable);
}
