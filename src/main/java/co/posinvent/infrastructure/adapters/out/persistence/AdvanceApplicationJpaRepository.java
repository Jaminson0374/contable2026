package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdvanceApplicationJpaRepository extends JpaRepository<AdvanceApplicationEntity, UUID> {

    List<AdvanceApplicationEntity> findByAdvancePaymentId(UUID advancePaymentId);
}
