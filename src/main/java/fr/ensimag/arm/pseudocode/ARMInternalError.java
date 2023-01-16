package fr.ensimag.arm.pseudocode;

/**
 * Internal error related to ARM code. Should never happen.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ARMInternalError extends RuntimeException {
    public ARMInternalError(String message, Throwable cause) {
        super(message, cause);
    }

    public ARMInternalError(String message) {
        super(message);
    }

    private static final long serialVersionUID = 3929345355905773360L;

}
