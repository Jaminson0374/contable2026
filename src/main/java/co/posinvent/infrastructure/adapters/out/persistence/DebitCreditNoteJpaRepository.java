package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DebitCreditNoteJpaRepository extends JpaRepository<DebitCreditNoteEntity, UUID> {

    List<DebitCreditNoteEntity> findBySupplierId(UUID supplierId);

    List<DebitCreditNoteEntity> findBySupplierInvoiceId(UUID supplierInvoiceId);

    @Query("""
        SELECT d FROM DebitCreditNoteEntity d
        WHERE (:type IS NULL OR d.type = :type)
          AND (:supplierId IS NULL OR d.supplierId = :supplierId)
        ORDER BY d.createdAt DESC
    """)
    Page<DebitCreditNoteEntity> findFiltered(
            @Param("type") String type,
            @Param("supplierId") UUID supplierId,
            Pageable pageable
    );
}
