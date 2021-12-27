package org.ck.mediatorj.messaging;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Dec 8, 2021
 *
 */
@Slf4j
class MessageApplicationEventListener<M extends Message<R>, R> implements GenericApplicationListener {

    @Getter
    private final ResolvableType requestType;

    @Getter
    private final ResolvableType responseType;

    private final MessageHandler<M, R> handler;

    public MessageApplicationEventListener(MessageHandler<M, R> handler) {

        ResolvableType messageType = resolveMessageType(handler);

        this.requestType = messageType;
        this.responseType = messageType.as(Message.class).getGeneric(0);
        this.handler = handler;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        try {

            @SuppressWarnings("unchecked")
            MessageApplicationEvent<M, R> messageEvent = (MessageApplicationEvent<M, R>) event;

            handleMessage(messageEvent);

        } catch (ClassCastException e) {

            log.error("This should not happened, the supportsEventType method guarantee the type safety");
        }

    }

    private void handleMessage(MessageApplicationEvent<M, R> messageEvent) {

        if (messageEvent.isResponsible() && messageEvent.getHandledCount() > 0) {
            throw new MessageHandlingException("Responsible message can only be handled once");
        }

        try {

            R response = handler.handleMessage(messageEvent.getPayload());

            messageEvent.setResponse(response);

            log.info("Message '{}' handled {} time(s)", requestType, messageEvent.getHandledCount());

        } catch (Throwable e) {

            log.info("Failed handle Message '{}'", requestType);

            messageEvent.setFailed(e);
        }

    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {

        ResolvableType messageEventType = eventType.as(MessageApplicationEvent.class);

        if (messageEventType == ResolvableType.NONE) {
            return false;
        }

        ResolvableType requestType = messageEventType.getGeneric(0);
        ResolvableType responseType = messageEventType.getGeneric(1);

        if (log.isDebugEnabled()) {
            log.debug("'{}' is checking, request: [{}], response: [{}]",
                ResolvableType.forClassWithGenerics(this.getClass(), this.requestType, this.responseType),
                requestType,
                responseType);
        }

        if (!responseType.toString().equals(this.responseType.toString())) {
            return false;
        }

        if (supportsMutlicast(messageEventType)) {
            return this.requestType.isAssignableFrom(requestType);
        }

        return this.requestType.resolve().equals(requestType.resolve());
    }

    private ResolvableType resolveMessageType(MessageHandler<M, R> handler) {
        return ResolvableType.forClass(AopUtils.getTargetClass(handler)).as(MessageHandler.class).getGeneric(0);
    }

    private boolean supportsMutlicast(ResolvableType messageEventType) {

        ResolvableType responseType = messageEventType.getGeneric(1);

        return responseType.resolve(Void.class).equals(Void.class);
    }

    public boolean isResponsible() {
        return !this.responseType.resolve().equals(Void.class);
    }

}
