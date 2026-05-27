package co.posinvent.domain.exception;

public class InvalidUploadException extends RuntimeException {

    private final String errorCode;

    public InvalidUploadException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
