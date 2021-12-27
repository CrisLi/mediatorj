package org.ck.mediatorj.event;

import org.ck.mediatorj.messaging.VoidMessageHandler;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface EventHandler<E extends EventMessage> extends VoidMessageHandler<E> {

    @Override
    void handle(E event);

}
