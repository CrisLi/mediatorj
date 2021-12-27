package org.ck.mediatorj.messaging;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;

import lombok.Getter;

/**
 * 
 * @author Chris
 * @since Dec 8, 2021
 *
 */
class MessageApplicationEvent<M extends Message<R>, R> extends PayloadApplicationEvent<M> {

    private static final long serialVersionUID = -5218165812164889129L;

    @Getter
    private final ResolvableType requestType;

    private final ResolvableType resolvableType;

    private CompletableFuture<R> callback;

    private int handledCount;

    public MessageApplicationEvent(Object source, M request) {
        super(source, request);
        this.requestType = ResolvableType.forInstance(request);
        this.resolvableType = resolveMessageType(request);
        this.callback = new CompletableFuture<R>();
    }

    @Override
    public ResolvableType getResolvableType() {
        return this.resolvableType;
    }

    public boolean isResponsible() {
        return !this.resolvableType.resolveGeneric(1).equals(Void.class);
    }

    synchronized void setResponse(R response) {
        this.callback.complete(response);
        this.handledCount++;
    }

    synchronized void setFailed(Throwable error) {
        this.callback.completeExceptionally(error);
        this.handledCount++;
    }

    synchronized int getHandledCount() {
        return this.handledCount;
    }

    CompletableFuture<R> getResponseCallback() {
        return this.callback;
    }

    private ResolvableType resolveMessageType(M request) {

        ResolvableType messageType = ResolvableType.forInstance(request);

        return ResolvableType.forClassWithGenerics(getClass(),
            messageType,
            messageType.as(Message.class).getGeneric(0));
    }

}
