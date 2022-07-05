package com.mpeixoto.product.exception;

/**
 * Customized exception.
 *
 * @author mpeixoto
 */
public class JsonPathException extends RuntimeException {
    /**
     * Default constructor of the class.
     *
     * @param message The message that will be shown when an JsonPathException be thrown
     * @param cause   The cause of the JsonPathException
     */
    public JsonPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
