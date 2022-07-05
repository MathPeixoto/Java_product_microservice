package com.mpeixoto.product.exception;

/**
 * Customized exception.
 *
 * @author mpeixoto
 */
public class AuthorizationException extends RuntimeException {
    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically
     * incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     * @since 1.4
     */
    public AuthorizationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a message and a cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     * @param cause   Type: {@link Throwable}
     */
    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
