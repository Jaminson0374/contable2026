package co.posinvent.application.dto;

import co.posinvent.domain.model.SalesDocumentStatus;
import jakarta.validation.constraints.NotNull;

public record TransitionRequest(
        @NotNull SalesDocumentStatus targetStatus
) {}
