package com.skiwi.bfcompiler.exceptions;

/**
 * @author Frank van Heeswijk
 */
public class InvalidSyntaxException extends RuntimeException {
    public InvalidSyntaxException(String message) {
        super(message);
    }
}
