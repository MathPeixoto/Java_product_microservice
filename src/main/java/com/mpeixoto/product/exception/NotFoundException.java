package com.mpeixoto.product.exception;

/**
 * Customized exception.
 *
 * @author mpeixoto
 */
public class NotFoundException extends RuntimeException {

    /**
     * Default constructor of the exception.
     *
     * @param message The reason why the exception occurred
     */
    public NotFoundException(String message) {
        super(message);
    }
}
