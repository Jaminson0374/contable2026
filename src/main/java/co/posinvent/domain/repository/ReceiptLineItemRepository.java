package co.posinvent.domain.repository;

import co.posinvent.domain.model.ReceiptLineItem;

import java.util.List;
import java.util.UUID;

public interface ReceiptLineItemRepository {

    List<ReceiptLineItem> saveAll(List<ReceiptLineItem> items);

    List<ReceiptLineItem> findByReceiptId(UUID receiptId);
}
