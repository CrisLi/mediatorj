package org.ck.mediatorj.messaging;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author Chris
 * @since Dec 10, 2021
 *
 */
public interface MessageBus {

    <M extends Message<R>, R> CompletableFuture<R> dispatch(M message);

    default <M extends Message<R>, R> R dispatchAndWait(M message) {

        try {

            CompletableFuture<R> callback = dispatch(message);

            return callback.get();

        } catch (InterruptedException | ExecutionException e) {

            Throwable cause = e.getCause();

            if (cause instanceof MessageHandlingException) {
                throw MessageHandlingException.class.cast(cause);
            }

            throw new MessageHandlingException(e.getMessage(), e.getCause());
        }

    }

}
