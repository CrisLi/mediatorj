package org.ck.mediatorj.event;

import java.time.Duration;

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
public class FailsafeEventPublisher implements EventPublisher {

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
    public <E extends EventMessage> void publish(E event) {
        Failsafe.with(retryPolicy, timeoutPolicy).run(() -> messageBus.dispatchAndWait(event));
    }

}
