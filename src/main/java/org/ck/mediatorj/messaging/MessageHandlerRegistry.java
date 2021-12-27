package org.ck.mediatorj.messaging;

import java.util.List;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface MessageHandlerRegistry {

    <H extends MessageHandler<?, ?>> void register(String beanName, Class<H> upperBoundHandlerType, H handler);

    void remove(String beanName);

    void cleanUp();

    <R, M extends Message<R>, H extends MessageHandler<M, R>> List<H> findHandlers(Message<R> message,
        Class<? extends H> upperBoundHandlerType);

}
