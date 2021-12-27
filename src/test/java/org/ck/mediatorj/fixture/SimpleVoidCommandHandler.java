package org.ck.mediatorj.fixture;

import org.ck.mediatorj.command.VoidCommandHandler;
import org.ck.mediatorj.command.VoidCommandMessage;
import org.ck.mediatorj.fixture.SimpleVoidCommandHandler.SimpleVoidCommand;

/**
 * 
 * @author Chris
 * @since Dec 3, 2021
 *
 */
public class SimpleVoidCommandHandler implements VoidCommandHandler<SimpleVoidCommand> {

    @Override
    public void handle(SimpleVoidCommand message) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

    }

    public static class SimpleVoidCommand implements VoidCommandMessage {

    }

}
