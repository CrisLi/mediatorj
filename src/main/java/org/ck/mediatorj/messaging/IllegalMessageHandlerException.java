package org.ck.mediatorj.messaging;

/**
 * 
 * @author Chris
 * @since Dec 2, 2021
 *
 */
public class IllegalMessageHandlerException extends MessageHandlingException {

    private static final long serialVersionUID = -3230827668582452017L;

    public IllegalMessageHandlerException() {
        super();
    }

    public IllegalMessageHandlerException(String message) {
        super(message);
    }

    public IllegalMessageHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

}
