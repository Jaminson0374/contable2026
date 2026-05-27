package co.posinvent.application.dto;

import java.util.List;
import java.util.UUID;

public record TransferRequest(
        UUID sourceWarehouseId,
        UUID targetWarehouseId,
        String notes,
        List<TransferItemRequest> items
) {}
