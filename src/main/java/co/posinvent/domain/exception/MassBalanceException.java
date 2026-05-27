package co.posinvent.domain.exception;

public final class MassBalanceException extends BusinessException {

    public MassBalanceException(String message) {
        super("MASS_BALANCE_OUT_OF_TOLERANCE", message);
    }
}
