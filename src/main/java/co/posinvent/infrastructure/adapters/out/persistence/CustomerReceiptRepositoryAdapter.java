package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CustomerReceipt;
import co.posinvent.domain.repository.CustomerReceiptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class CustomerReceiptRepositoryAdapter implements CustomerReceiptRepository {

    private final CustomerReceiptJpaRepository jpa;
    private final CustomerReceiptMapper mapper;

    CustomerReceiptRepositoryAdapter(CustomerReceiptJpaRepository jpa, CustomerReceiptMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public CustomerReceipt save(CustomerReceipt receipt) {
        var entity = mapper.toEntity(receipt);

        // Wire applications to receipt entity for cascade save
        if (entity.getApplications() != null) {
            for (var app : entity.getApplications()) {
                app.setReceipt(entity);
            }
        }

        var saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<CustomerReceipt> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<CustomerReceipt> findByClientId(UUID clientId, Pageable pageable) {
        return jpa.findByClientId(clientId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<CustomerReceipt> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }
}
