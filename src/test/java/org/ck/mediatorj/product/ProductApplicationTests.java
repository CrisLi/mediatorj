package org.ck.mediatorj.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.ck.mediatorj.core.Mediator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.SneakyThrows;

/**
 * 
 * @author Chris
 * @since Nov 19, 2021
 *
 */
@SpringBootTest(classes = ProductApplicationTestsConfig.class)
public class ProductApplicationTests {

    @Autowired
    private Mediator mediator;

    @Autowired
    private FakeDataStore fakeDataStore;

    @Test
    void testGetProductsQuery() {

        List<Product> products = mediator.send(new GetProductsQuery());

        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void testGetProductsWithFilterQuery() {

        List<Product> products = mediator.send(new GetProductsWithFilterQuery(2));

        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void testAddProductCommand() {

        Product product = new Product(4, "Milk");

        CompletableFuture<Void> result = mediator.send(new AddProductCommand(product));

        result.get();

        int productSize = fakeDataStore.productCount();

        assertThat(productSize).isEqualTo(4);

    }

    @Test
    @SneakyThrows
    void testRetryCommand() {

        RetryableCommand command = new RetryableCommand(3);

        CompletableFuture<Integer> result = mediator.send(command);

        int count = result.get();

        assertThat(count).isEqualTo(3);

    }

}
