package co.posinvent.application.dto;

import co.posinvent.domain.model.SalesDocumentType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record SalesDocumentRequest(
        @NotNull SalesDocumentType type,
        @NotNull UUID clientId,
        @NotNull UUID warehouseId,
        UUID shiftId,
        UUID cashRegisterId,
        UUID sourceDocumentId,
        LocalDate dueDate,
        Boolean isCreditSale
) {}
