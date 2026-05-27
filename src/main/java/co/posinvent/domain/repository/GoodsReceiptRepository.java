package co.posinvent.domain.repository;

import co.posinvent.domain.model.GoodsReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface GoodsReceiptRepository {

    GoodsReceipt save(GoodsReceipt goodsReceipt);

    GoodsReceipt saveAndFlush(GoodsReceipt goodsReceipt);

    Optional<GoodsReceipt> findById(UUID id);

    Page<GoodsReceipt> findAll(Pageable pageable);

    Page<GoodsReceipt> findByOcId(UUID ocId, Pageable pageable);
}
