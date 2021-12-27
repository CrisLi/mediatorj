package org.ck.mediatorj.command;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.ck.mediatorj.core.RetryableExecutionException;
import org.ck.mediatorj.messaging.MessageBus;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.failsafe.Timeout;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
@RequiredArgsConstructor
public class FailsafeCommandGateway implements CommandGateway {

    private final MessageBus messageBus;

    @Setter
    private RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
        .handle(RetryableExecutionException.class)
        .withDelay(Duration.ofMillis(500))
        .withMaxRetries(2)
        .build();

    @Setter
    private Timeout<Object> timeoutPolicy = Timeout.of(Duration.ofSeconds(30));

    @Override
    public <C extends CommandMessage<R>, R> CompletableFuture<R> send(C message) {
        return Failsafe.with(retryPolicy).getStageAsync(() -> messageBus.dispatch(message));
    }

    @Override
    public <C extends CommandMessage<R>, R> R sendAndWait(C command) {
        return Failsafe.with(retryPolicy, timeoutPolicy).get(() -> messageBus.dispatchAndWait(command));
    }

}
