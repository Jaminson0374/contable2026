package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ElectronicInvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ElectronicInvoiceJpaRepository extends JpaRepository<ElectronicInvoiceEntity, UUID> {

    @Query("SELECT e FROM ElectronicInvoiceEntity e WHERE e.salesDocument.id = :salesDocumentId")
    Optional<ElectronicInvoiceEntity> findBySalesDocumentId(@Param("salesDocumentId") UUID salesDocumentId);

    Optional<ElectronicInvoiceEntity> findByCufe(String cufe);

    List<ElectronicInvoiceEntity> findByStatus(ElectronicInvoiceStatus status);

    @Query("SELECT COUNT(e) FROM ElectronicInvoiceEntity e WHERE e.status = :status")
    long countByStatus(@Param("status") ElectronicInvoiceStatus status);

    @Query("SELECT COUNT(e) FROM ElectronicInvoiceEntity e WHERE e.createdAt >= CURRENT_DATE")
    long countIssuedToday();
}
