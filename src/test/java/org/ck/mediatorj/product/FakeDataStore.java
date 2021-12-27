package org.ck.mediatorj.product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 
 * @author Chris
 * @since Nov 19, 2021
 *
 */
public class FakeDataStore {

    private Map<Integer, Product> store = new ConcurrentHashMap<>();

    public FakeDataStore() {

        addProduct(new Product(1, "Cellphone"));
        addProduct(new Product(2, "Laptop"));
        addProduct(new Product(3, "Headset"));

    }

    public void addProduct(Product product) {
        store.put(product.getId(), product);
    }

    public List<Product> getAllProducts() {
        return store.values().stream().collect(Collectors.toList());
    }

    public int productCount() {
        return store.size();
    }

}
