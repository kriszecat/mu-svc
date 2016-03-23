package com.hq.activity.sample.repository;

import com.hq.activity.sample.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Product findByProductNumber(String category);
}
