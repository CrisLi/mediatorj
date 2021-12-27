package org.ck.mediatorj.messaging;

/**
 * 
 * @author Chris
 * @since Dec 2, 2021
 *
 */
public class NoMessageHandlerFoundException extends MessageHandlingException {

    private static final long serialVersionUID = 5348386500604336042L;

    public NoMessageHandlerFoundException() {
        super();
    }

    public NoMessageHandlerFoundException(String message) {
        super(message);
    }

    public NoMessageHandlerFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
