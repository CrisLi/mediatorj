package org.ck.mediatorj.core;

/**
 * 
 * @author Chris
 * @since Nov 24, 2021
 *
 */
public class RetryableExecutionException extends RuntimeException {

    private static final long serialVersionUID = -7375733727135789946L;

    public RetryableExecutionException() {
        super();
    }

    public RetryableExecutionException(String message) {
        super(message);
    }

    public RetryableExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
