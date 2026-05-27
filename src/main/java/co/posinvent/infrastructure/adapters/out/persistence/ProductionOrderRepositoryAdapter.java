package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionOrder;
import co.posinvent.domain.model.ProductionOrderStatus;
import co.posinvent.domain.repository.ProductionOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductionOrderRepositoryAdapter implements ProductionOrderRepository {
    private final ProductionOrderJpaRepository jpa;
    private final ProductionOrderMapper mapper;

    ProductionOrderRepositoryAdapter(ProductionOrderJpaRepository jpa, ProductionOrderMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override public ProductionOrder save(ProductionOrder o) { return mapper.toDomain(jpa.save(mapper.toEntity(o))); }
    @Override public Optional<ProductionOrder> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }
    @Override public Page<ProductionOrder> findAll(Pageable p) { return jpa.findAll(p).map(mapper::toDomain); }

    @Override
    public Page<ProductionOrder> findFiltered(ProductionOrderStatus s, UUID w, LocalDate f, LocalDate t, Pageable p) {
        return jpa.findFiltered(s, w, f, t, p).map(mapper::toDomain);
    }

    @Override
    public Optional<ProductionOrder> findByOrderNumber(String n) { return jpa.findByOrderNumber(n).map(mapper::toDomain); }
}
