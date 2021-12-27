package org.ck.mediatorj.product;

import java.util.List;

import org.ck.mediatorj.query.QueryHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Nov 19, 2021
 *
 */
@Slf4j
@RequiredArgsConstructor
public class GetProductsQueryHandler implements QueryHandler<GetProductsQuery, List<Product>> {

    private final FakeDataStore fakeDataStore;

    @Override
    public List<Product> handleMessage(GetProductsQuery request) {

        log.info("Handle [{}] request", request.getClass().getName());

        return fakeDataStore.getAllProducts();
    }

}
