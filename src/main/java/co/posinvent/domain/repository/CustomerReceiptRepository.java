package co.posinvent.domain.repository;

import co.posinvent.domain.model.CustomerReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerReceiptRepository {

    CustomerReceipt save(CustomerReceipt receipt);

    Optional<CustomerReceipt> findById(UUID id);

    Page<CustomerReceipt> findByClientId(UUID clientId, Pageable pageable);

    Page<CustomerReceipt> findAll(Pageable pageable);
}
