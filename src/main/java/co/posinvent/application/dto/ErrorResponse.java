package co.posinvent.application.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        OffsetDateTime timestamp,
        String errorCode,
        String message,
        List<String> details
) {
    public static ErrorResponse of(String errorCode, String message) {
        return new ErrorResponse(OffsetDateTime.now(), errorCode, message, List.of());
    }
}
