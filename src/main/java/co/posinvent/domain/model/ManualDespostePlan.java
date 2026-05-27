package co.posinvent.domain.model;

import co.posinvent.domain.model.Batch.BatchStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ManualDespostePlan(
        UUID sourceBatchId,
        DesposteMassBalance massBalance,
        BigDecimal totalCommercialValue,
        BigDecimal totalAllocatedCost,
        List<ManualDesposteCutResult> cuts,
        List<StockUpsertDraft> stockUpserts,
        SourceBatchTransition sourceBatchTransition
) {
    public ManualDespostePlan {
        cuts = List.copyOf(cuts);
        stockUpserts = List.copyOf(stockUpserts);
    }

    public enum DesposteSourceType { MANUAL }

    public enum SourceBatchAction { CLOSE, KEEP_PROCESSING }

    public record Command(
            UUID sourceBatchId,
            DesposteSourceType sourceType,
            String manualJustification,
            BigDecimal wasteWeight,
            BigDecimal shrinkWeight,
            String notes,
            List<ManualDesposteCutCommand> cuts
    ) {
        public Command {
            cuts = List.copyOf(cuts);
        }
    }

    public record ManualDesposteCutCommand(
            UUID productId,
            UUID warehouseId,
            BigDecimal weight,
            BigDecimal suggestedSalePrice
    ) { }

    public record DesposteMassBalance(
            BigDecimal inputWeight,
            BigDecimal totalCutsWeight,
            BigDecimal wasteWeight,
            BigDecimal shrinkWeight,
            BigDecimal deviation,
            BigDecimal tolerance,
            boolean withinTolerance
    ) { }

    public record ManualDesposteCutResult(
            UUID productId,
            UUID warehouseId,
            BigDecimal weight,
            BigDecimal suggestedSalePrice,
            BigDecimal commercialValue,
            BigDecimal allocatedCost,
            BigDecimal unitCost
    ) { }

    public record StockUpsertDraft(
            UUID productId,
            UUID batchId,
            UUID warehouseId,
            BigDecimal quantityDelta,
            BigDecimal unitCost
    ) { }

    public record SourceBatchTransition(
            UUID batchId,
            BatchStatus previousStatus,
            BatchStatus nextStatus,
            SourceBatchAction action
    ) { }
}
