package com.hq.activity.test;


import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(produces = {APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "http://localhost:9000")
public class ProductController {

    List<Product> products = new ArrayList<Product>();

    public ProductController() {
        products.add(new Product(1, "Bananas", "Fruit", 2.42));
        products.add(new Product(2, "Pears", "Fruit", 2.02));
        products.add(new Product(3, "Tuna", "Fish", 20.45));
        products.add(new Product(4, "Salmon", "Fish", 17.93));
        products.add(new Product(5, "Trout", "Fish", 12.93));
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Collection<Product> getAllProducts() {
        System.out.println("ProductController.getAllProducts");
        return products;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public void createProduct(@RequestBody Product product) {
        int nextId = 1;
        if (!products.isEmpty()) {
            Product p = products.stream().max((p1,p2) -> p1.getId() - p2.getId()).get();
            nextId = p.getId() + 1;
        }
        products.add(new Product(product, nextId));
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id) {
        Product product = products.stream().filter(p -> p.getId() == id).findAny().get();
        if (product != null) {
            return product;
        } else {
            return new Product();
        }
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable int id, @RequestBody Product update) {
        Product product = products.stream().filter(p -> p.getId() == id).findAny().get();
        if (product != null) {
            if (!update.getName().isEmpty()) product.setName(update.getName());
            if (!update.getCategory().isEmpty()) product.setCategory(update.getCategory());
            if (!update.getPrice().isNaN()) product.setPrice(update.getPrice());
            return product;
        }
        return new Product();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id) {
        Product product = products.stream().filter(p -> p.getId() == id).findAny().get();
        if (product != null) {
            products.remove(product);
        }
    }
}
