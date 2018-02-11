package com.kustov.webproject.exception;


/**
 * The Class ConnectionException.
 */
public class ConnectionException extends Exception{

    /**
     * Instantiates a new connection exception.
     */
    public ConnectionException() {
    }

    /**
     * Instantiates a new connection exception.
     *
     * @param message the message
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new connection exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new connection exception.
     *
     * @param cause the cause
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
