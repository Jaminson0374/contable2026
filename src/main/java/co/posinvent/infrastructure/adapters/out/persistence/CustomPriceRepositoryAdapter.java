package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CustomPrice;
import co.posinvent.domain.repository.CustomPriceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class CustomPriceRepositoryAdapter implements CustomPriceRepository {

    private final CustomPriceJpaRepository jpa;
    private final CustomPriceMapper mapper;

    CustomPriceRepositoryAdapter(CustomPriceJpaRepository jpa, CustomPriceMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Optional<CustomPrice> findByClientIdAndProductId(UUID clientId, UUID productId) {
        return jpa.findByClientIdAndProductId(clientId, productId).map(mapper::toDomain);
    }

    @Override
    public List<CustomPrice> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<CustomPrice> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CustomPrice> findByClientId(UUID clientId) {
        return jpa.findByClientId(clientId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<CustomPrice> findByProductId(UUID productId) {
        return jpa.findByProductId(productId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public CustomPrice save(CustomPrice customPrice) {
        var entity = mapper.toEntity(customPrice);
        if (customPrice.id() != null) {
            var existing = jpa.findById(customPrice.id());
            if (existing.isPresent()) {
                entity = existing.get();
                entity.setClientId(customPrice.clientId());
                entity.setProductId(customPrice.productId());
                entity.setPrice(customPrice.price());
                entity.setTaxType(customPrice.taxType());
                entity.setTaxRate(customPrice.taxRate());
            }
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public void delete(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsByClientIdAndProductId(UUID clientId, UUID productId) {
        return jpa.existsByClientIdAndProductId(clientId, productId);
    }
}
