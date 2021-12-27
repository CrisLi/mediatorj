package org.ck.mediatorj.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import lombok.Getter;

/**
 * 
 * @author Chris
 * @since Dec 10, 2021
 *
 */
public class PayloadEvent<T> implements EventMessage, ResolvableTypeProvider {

    @Getter
    private final T payload;

    private ResolvableType resolvableType;

    public PayloadEvent(T payload, Class<T> payloadType) {
        this.payload = payload;
        this.resolvableType = ResolvableType.forClassWithGenerics(getClass(), payloadType);
    }

    public PayloadEvent(T payload, Class<? super T> type, Class<?>... generics) {
        this.payload = payload;
        this.resolvableType = ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forClassWithGenerics(type, generics));
    }

    @Override
    public ResolvableType getResolvableType() {
        return this.resolvableType;
    }

}
