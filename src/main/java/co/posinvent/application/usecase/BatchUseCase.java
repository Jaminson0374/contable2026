package co.posinvent.application.usecase;

import co.posinvent.application.dto.BatchRequest;
import co.posinvent.application.dto.BatchResponse;
import co.posinvent.application.dto.PageResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.Batch;
import co.posinvent.domain.model.Batch.BatchStatus;
import co.posinvent.domain.repository.BatchRepository;
import co.posinvent.domain.repository.ThirdPartyRepository;
import co.posinvent.domain.repository.WarehouseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BatchUseCase {

    private final BatchRepository     batchRepository;
    private final ThirdPartyRepository thirdPartyRepository;
    private final WarehouseRepository  warehouseRepository;

    public BatchUseCase(
            BatchRepository batchRepository,
            ThirdPartyRepository thirdPartyRepository,
            WarehouseRepository warehouseRepository
    ) {
        this.batchRepository      = batchRepository;
        this.thirdPartyRepository = thirdPartyRepository;
        this.warehouseRepository  = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<BatchResponse> list(Pageable pageable) {
        return PageResponse.from(batchRepository.findAll(pageable), BatchResponse::from);
    }

    @Transactional(readOnly = true)
    public PageResponse<BatchResponse> listByStatus(BatchStatus status, Pageable pageable) {
        return PageResponse.from(batchRepository.findByStatus(status, pageable), BatchResponse::from);
    }

    @Transactional(readOnly = true)
    public BatchResponse getById(UUID id) {
        return batchRepository.findById(id)
                .map(BatchResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));
    }

    @Transactional
    public BatchResponse create(BatchRequest request, UUID operatorId) {
        // Validar que el proveedor exista
        thirdPartyRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor", request.supplierId()));

        // Validar que la bodega exista y sea de tipo CANAL (solo entra materia prima ahí)
        var warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Bodega", request.warehouseId()));

        if (warehouse.warehouseType() != co.posinvent.domain.model.Warehouse.WarehouseType.CANAL) {
            throw new BusinessException("INVALID_WAREHOUSE_TYPE",
                    "Los lotes de entrada solo se registran en bodegas de tipo CANAL. " +
                    "Bodega seleccionada: " + warehouse.warehouseType());
        }

        var batch = new Batch(
                null,
                request.supplierId(),
                request.warehouseId(),
                request.entryDate(),
                request.initialWeight(),
                request.purchaseCost(),
                BatchStatus.OPEN,
                request.notes(),
                null,                // expirationDate
                operatorId,
                null,
                null,
                null,   // sourceReceiptId — entrada manual, no asociado a recepción
                null    // ocId — entrada manual, no asociado a OC
        );

        // TODO Sprint 7: generar Journal Entry de compra
        // Débito: Inventario (activo) / Crédito: Cuentas por pagar (pasivo)

        return BatchResponse.from(batchRepository.save(batch));
    }

    @Transactional
    public BatchResponse updateStatus(UUID id, BatchStatus newStatus) {
        var existing = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote", id));

        // Un lote CLOSED es inmutable — regla de integridad del dominio
        if (existing.status() == BatchStatus.CLOSED) {
            throw new BusinessException("BATCH_IMMUTABLE",
                    "Un lote cerrado no puede modificarse. Usá un Ajuste de Inventario.");
        }

        var updated = new Batch(
                existing.id(), existing.supplierId(), existing.warehouseId(),
                existing.entryDate(), existing.initialWeight(), existing.purchaseCost(),
                newStatus, existing.notes(), null, existing.createdBy(),
                existing.createdAt(), null,
                existing.sourceReceiptId(),
                existing.ocId()
        );

        return BatchResponse.from(batchRepository.save(updated));
    }
}
