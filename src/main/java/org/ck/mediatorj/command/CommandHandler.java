package org.ck.mediatorj.command;

import org.ck.mediatorj.messaging.MessageHandler;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface CommandHandler<C extends CommandMessage<R>, R> extends MessageHandler<C, R> {

    @Override
    default R handleMessage(C command) {

        return handle(command);
    }

    R handle(C command);

}
