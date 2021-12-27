package org.ck.mediatorj.fixture;

import org.ck.mediatorj.command.CommandHandler;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommand;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommandResponse;
import org.ck.mediatorj.fixture.SubSimpleCommandHandler.SubSimpleCommand;

/**
 * 
 * @author Chris
 * @since Dec 2, 2021
 *
 */
public class SubSimpleCommandHandler implements CommandHandler<SubSimpleCommand, SimpleCommandResponse> {

    @Override
    public SimpleCommandResponse handle(SubSimpleCommand command) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

        return new SimpleCommandResponse();
    }

    public static class SubSimpleCommand extends SimpleCommand {

    }

}
