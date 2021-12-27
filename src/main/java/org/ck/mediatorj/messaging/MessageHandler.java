package org.ck.mediatorj.messaging;

/**
 * 
 * @author Chris
 * @since Nov 25, 2021
 *
 */
@FunctionalInterface
public interface MessageHandler<M extends Message<R>, R> {

    R handleMessage(M message);

}
