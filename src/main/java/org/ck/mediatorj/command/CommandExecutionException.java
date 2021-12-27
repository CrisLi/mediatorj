package org.ck.mediatorj.command;

/**
 * 
 * @author Chris
 * @since Nov 17, 2021
 *
 */
public class CommandExecutionException extends RuntimeException {

    private static final long serialVersionUID = -4238672265927968223L;

    public CommandExecutionException() {
        super();
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
