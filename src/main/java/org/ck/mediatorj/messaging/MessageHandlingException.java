package org.ck.mediatorj.messaging;

/**
 * 
 * @author Chris
 * @since Dec 2, 2021
 *
 */
public class MessageHandlingException extends RuntimeException {

    private static final long serialVersionUID = -7603579213986648355L;

    public MessageHandlingException() {
        super();
    }

    public MessageHandlingException(String message) {
        super(message);
    }

    public MessageHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

}
