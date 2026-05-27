package co.posinvent.application.dto;

import co.posinvent.domain.model.ProfitabilityRow;
import java.util.List;

public record ProfitabilityResponse(
        List<ProfitabilityRow> rows,
        String from,
        String to
) {}
