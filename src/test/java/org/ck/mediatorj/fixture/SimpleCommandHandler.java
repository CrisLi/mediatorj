package org.ck.mediatorj.fixture;

import org.ck.mediatorj.command.CommandHandler;
import org.ck.mediatorj.command.CommandMessage;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommand;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommandResponse;

/**
 * 
 * @author Chris
 * @since Dec 1, 2021
 *
 */
public class SimpleCommandHandler implements CommandHandler<SimpleCommand, SimpleCommandResponse> {

    @Override
    public SimpleCommandResponse handle(SimpleCommand command) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

        return new SimpleCommandResponse();
    }

    public static class SimpleCommandResponse {

    }

    public static class SimpleCommand implements CommandMessage<SimpleCommandResponse> {

    }

}
