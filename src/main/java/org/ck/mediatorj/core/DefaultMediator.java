package org.ck.mediatorj.core;

import java.util.concurrent.CompletableFuture;

import org.ck.mediatorj.command.CommandMessage;
import org.ck.mediatorj.command.CommandGateway;
import org.ck.mediatorj.event.EventMessage;
import org.ck.mediatorj.event.EventPublisher;
import org.ck.mediatorj.query.QueryMessage;
import org.ck.mediatorj.query.QueryGateway;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Chris
 * @since Nov 19, 2021
 *
 */
@RequiredArgsConstructor
public class DefaultMediator implements Mediator {

    private final CommandGateway commandSender;

    private final QueryGateway querySender;

    private final EventPublisher publisher;

    @Override
    public <C extends CommandMessage<R>, R> CompletableFuture<R> send(C message) {
        return commandSender.send(message);
    }

    @Override
    public <C extends CommandMessage<R>, R> R sendAndWait(C message) {
        return commandSender.sendAndWait(message);
    }

    @Override
    public <Q extends QueryMessage<R>, R> R send(Q query) {
        return querySender.send(query);
    }

    @Override
    public void publish(EventMessage event) {
        publisher.publish(event);
    }

}
