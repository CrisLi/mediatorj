package org.ck.mediatorj.product;

import org.ck.mediatorj.command.CommandMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Chris
 * @since Nov 25, 2021
 *
 */
@Getter
@RequiredArgsConstructor
public class RetryableCommand implements CommandMessage<Integer> {

    private final int maxRetryCount;

}
