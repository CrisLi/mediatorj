package org.ck.mediatorj.product;

import org.ck.mediatorj.configuration.MediatorConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @author Chris
 * @since Nov 22, 2021
 *
 */
@Configuration
@Import(MediatorConfiguration.class)
public class ProductApplicationTestsConfig {

    @Bean
    FakeDataStore fakeDataStore() {
        return new FakeDataStore();
    }

    @Bean
    GetProductsQueryHandler getProductsHandler() {
        return new GetProductsQueryHandler(fakeDataStore());
    }

    @Bean
    GetProductsWithFilterQueryHandler getProductsWithFilterHandler() {
        return new GetProductsWithFilterQueryHandler(fakeDataStore());
    }

    @Bean
    AddProductCommandHandler addProductCommandHandler() {
        return new AddProductCommandHandler(fakeDataStore());
    }

    @Bean
    RetryableCommandHandler retryableCommandHandler() {
        return new RetryableCommandHandler();
    }

}
