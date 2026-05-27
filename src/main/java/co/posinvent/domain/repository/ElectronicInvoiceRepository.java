package co.posinvent.domain.repository;

import co.posinvent.domain.model.DianSyncQueueItem;
import co.posinvent.domain.model.ElectronicInvoice;
import co.posinvent.domain.model.ElectronicInvoiceStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ElectronicInvoiceRepository {
    ElectronicInvoice save(ElectronicInvoice invoice);
    Optional<ElectronicInvoice> findById(UUID id);
    List<ElectronicInvoice> findAll(int page, int size);
    Optional<ElectronicInvoice> findBySalesDocumentId(UUID salesDocumentId);
    Optional<ElectronicInvoice> findByCufe(String cufe);
    List<ElectronicInvoice> findByStatus(ElectronicInvoiceStatus status, int page, int size);
    long countByStatus(ElectronicInvoiceStatus status);
    long countIssuedToday();
    DianSyncQueueItem saveSyncQueueItem(DianSyncQueueItem item);
    void updateSyncQueueItem(DianSyncQueueItem item);
    List<DianSyncQueueItem> findPendingSyncItems();
    Optional<DianSyncQueueItem> findSyncQueueItemByInvoiceId(UUID invoiceId);
}
