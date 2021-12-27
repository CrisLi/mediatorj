package org.ck.mediatorj.product;

import org.ck.mediatorj.command.VoidCommandHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Nov 23, 2021
 *
 */
@Slf4j
@RequiredArgsConstructor
public class AddProductCommandHandler implements VoidCommandHandler<AddProductCommand> {

    private final FakeDataStore fakeDataStore;

    @Override
    public void handle(AddProductCommand request) {

        log.info("Handle [{}] request", request.getClass().getName());

        fakeDataStore.addProduct(request.getProduct());
    }

}
