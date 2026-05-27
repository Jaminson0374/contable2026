package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.PriceList;
import co.posinvent.domain.repository.PriceListRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class PriceListRepositoryAdapter implements PriceListRepository {

    private final PriceListJpaRepository jpa;
    private final PriceListMapper mapper;

    PriceListRepositoryAdapter(PriceListJpaRepository jpa, PriceListMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<PriceList> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<PriceList> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public PriceList save(PriceList domain) {
        return mapper.toDomain(jpa.save(mapper.toEntity(domain)));
    }

    @Override
    public boolean existsByCode(String code) {
        return jpa.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, UUID id) {
        return jpa.existsByCodeAndIdNot(code, id);
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
