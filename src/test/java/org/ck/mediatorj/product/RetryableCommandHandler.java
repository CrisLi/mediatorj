package org.ck.mediatorj.product;

import java.util.concurrent.atomic.AtomicInteger;

import org.ck.mediatorj.command.CommandHandler;
import org.ck.mediatorj.core.RetryableExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Nov 25, 2021
 *
 */
@Slf4j
public class RetryableCommandHandler implements CommandHandler<RetryableCommand, Integer> {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Integer handle(RetryableCommand request) {

        int i = count.incrementAndGet();

        log.info("RetryableCommand executed {} time(s)", i);

        if (i < request.getMaxRetryCount()) {
            throw new RetryableExecutionException();
        }

        return count.get();
    }

}
