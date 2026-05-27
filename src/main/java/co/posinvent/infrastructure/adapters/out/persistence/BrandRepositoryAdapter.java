package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Brand;
import co.posinvent.domain.repository.BrandRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class BrandRepositoryAdapter implements BrandRepository {

    private final BrandJpaRepository jpa;
    private final BrandMapper mapper;

    BrandRepositoryAdapter(BrandJpaRepository jpa, BrandMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<Brand> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Brand> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Brand save(Brand domain) {
        return mapper.toDomain(jpa.save(mapper.toEntity(domain)));
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return jpa.existsByNameAndIdNot(name, id);
    }
}
