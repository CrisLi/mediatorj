package org.ck.mediatorj.command;

/**
 * 
 * @author Chris
 * @since Nov 17, 2021
 *
 */
public class CommandHandlerNotFoundException extends CommandExecutionException {

    private static final long serialVersionUID = -3921151110635310072L;

    public CommandHandlerNotFoundException() {
        super();
    }

    public CommandHandlerNotFoundException(String message) {
        super(message);
    }

    public CommandHandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
