package org.ck.mediatorj.messaging;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author Chris
 * @since Dec 10, 2021
 *
 */
class MessageApplicationEventCallback<R> extends CompletableFuture<R> {

    int handleCount;

    @Override
    public boolean complete(R response) {

        if (this.isDone()) {

        }

        return super.complete(response);
    }

    public R getResponse() {
        try {
            return super.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MessageHandlingException();
        }
    }

}
