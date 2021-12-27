package org.ck.mediatorj.product;

import org.ck.mediatorj.command.VoidCommandMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Chris
 * @since Nov 23, 2021
 *
 */
@Getter
@RequiredArgsConstructor
public class AddProductCommand implements VoidCommandMessage {

    private final Product product;

}
