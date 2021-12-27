package org.ck.mediatorj.messaging;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface VoidMessageHandler<C extends Message<Void>> extends MessageHandler<C, Void> {

    @Override
    default Void handleMessage(C message) {

        handle(message);

        return null;
    }

    void handle(C message);

}
