package org.ck.mediatorj.product;

import java.util.List;
import java.util.stream.Collectors;

import org.ck.mediatorj.query.QueryHandler;

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
public class GetProductsWithFilterQueryHandler implements QueryHandler<GetProductsWithFilterQuery, List<Product>> {

    private final FakeDataStore fakeDataStore;

    @Override
    public List<Product> handleMessage(GetProductsWithFilterQuery request) {

        log.info("Handle [{}] request", request.getClass().getName());

        return fakeDataStore.getAllProducts().stream()
            .limit(request.getMaxCount())
            .collect(Collectors.toList());
    }

}
