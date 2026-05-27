package co.posinvent.domain.repository;

import co.posinvent.domain.model.StockTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface StockTransferRepository {

    StockTransfer save(StockTransfer transfer);

    Optional<StockTransfer> findById(UUID id);

    Page<StockTransfer> findAll(Pageable pageable);
}
