package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CashRegisterJpaRepository extends JpaRepository<CashRegisterEntity, UUID> {

    List<CashRegisterEntity> findByActiveTrueOrderByName();
}
