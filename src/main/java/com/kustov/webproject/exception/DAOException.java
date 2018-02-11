package com.kustov.webproject.exception;


/**
 * The Class DAOException.
 */
public class DAOException extends Exception {

    /**
     * Instantiates a new DAO exception.
     */
    public DAOException() {
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param message the message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param cause the cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}
