package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class AdjustmentAppliedEvent {
    private final UUID adjustmentId;
    private final BigDecimal delta;
    private final String reason;
    private final Object source;

    public AdjustmentAppliedEvent(Object source, UUID adjustmentId, BigDecimal delta, String reason) {
        this.source = source; this.adjustmentId = adjustmentId; this.delta = delta; this.reason = reason;
    }
    public Object getSource() { return source; }
    public UUID adjustmentId() { return adjustmentId; }
    public BigDecimal delta() { return delta; }
    public String reason() { return reason; }
}
