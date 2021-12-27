package org.ck.mediatorj.command;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface CommandGateway {

    <C extends CommandMessage<R>, R> CompletableFuture<R> send(C command);

    <C extends CommandMessage<R>, R> R sendAndWait(C command);

}
