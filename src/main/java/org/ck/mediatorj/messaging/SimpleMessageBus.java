package org.ck.mediatorj.messaging;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Dec 10, 2021
 *
 */
@Slf4j
public class SimpleMessageBus implements MessageBus, ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public <M extends Message<R>, R> CompletableFuture<R> dispatch(M message) {

        MessageApplicationEvent<M, R> event = new MessageApplicationEvent<M, R>(this, message);

        eventPublisher.publishEvent(event);

        log.info("Message '{}' dispatched", message.getClass().getName());

        CompletableFuture<R> result = event.getResponseCallback();

        if (result.isDone()) {
            return result;
        }

        log.error("No MessageHandler found for message '{}'", event.getRequestType());

        throw new NoMessageHandlerFoundException("No MessageHandler found for '" + event.getRequestType() + "'");

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

}
