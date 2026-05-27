package co.posinvent.domain.exception;

public class StorageException extends RuntimeException {

    private final String errorCode;

    public StorageException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
