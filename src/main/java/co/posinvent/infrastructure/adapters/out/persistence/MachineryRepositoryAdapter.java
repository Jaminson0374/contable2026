package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Machinery;
import co.posinvent.domain.repository.MachineryRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class MachineryRepositoryAdapter implements MachineryRepository {
    private final MachineryJpaRepository jpa;
    private final MachineryMapper mapper;
    MachineryRepositoryAdapter(MachineryJpaRepository j, MachineryMapper m) { this.jpa = j; this.mapper = m; }

    @Override public Machinery save(Machinery m) { return mapper.toDomain(jpa.save(mapper.toEntity(m))); }
    @Override public Optional<Machinery> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }
    @Override public List<Machinery> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }
    @Override public void delete(UUID id) { jpa.deleteById(id); }
}
