package org.ck.mediatorj.product;

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
public class GetProductsWithFilterQuery extends GetProductsQuery {

    private final int maxCount;

}
