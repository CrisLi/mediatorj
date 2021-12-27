package org.ck.mediatorj.fixture;

import java.util.Collections;
import java.util.List;

import org.ck.mediatorj.command.CommandHandler;
import org.ck.mediatorj.command.CommandMessage;
import org.ck.mediatorj.fixture.SimpleListCommandHandler.SimpleListCommand;
import org.ck.mediatorj.fixture.SimpleListCommandHandler.SimpleListCommandResponse;

/**
 * 
 * @author Chris
 * @since Dec 1, 2021
 *
 */
public class SimpleListCommandHandler implements CommandHandler<SimpleListCommand, List<SimpleListCommandResponse>> {

    @Override
    public List<SimpleListCommandResponse> handle(SimpleListCommand command) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

        return Collections.emptyList();
    }

    public static class SimpleListCommandResponse {

    }

    public static class SimpleListCommand implements CommandMessage<List<SimpleListCommandResponse>> {

    }

}
