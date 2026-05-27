package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

interface MachineryJpaRepository extends JpaRepository<MachineryEntity, UUID> {}