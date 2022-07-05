package com.mpeixoto.product.exception;

/**
 * Customized exception.
 *
 * @author mpeixoto
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically
     * incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link
     *                #getMessage()} method).
     * @since 1.4
     */
    public BadRequestException(String message) {
        super(message);
    }
}
