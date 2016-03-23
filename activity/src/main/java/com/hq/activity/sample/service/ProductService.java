package com.hq.activity.sample.service;

import com.hq.activity.exception.NotFoundException;
import com.hq.activity.sample.bean.ProductVO;
import com.hq.activity.sample.entity.Product;
import com.hq.activity.sample.repository.ProductRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Repository // Enable exception translation from JPA exceptions to Spring’s DataAccessException hierarchy
@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    public ProductVO createProduct(ProductVO vo) {
        Product product = repository.save(convert(vo));
        return convert(product);
    }

    public ProductVO updateProduct(ProductVO vo) throws NotFoundException {
        Product product = repository.findByProductNumber(vo.getProductNumber());
        if (product == null) {
            throw new NotFoundException();
        } else {
            Product updatedProduct = repository.save(assign(product, vo));
            return convert(updatedProduct);
        }
    }

    public ProductVO deleteProduct(String productNumber) throws NotFoundException {
        Product product = repository.findByProductNumber(productNumber);
        if (product == null) {
            throw new NotFoundException();
        } else {
            repository.delete(product);
            return convert(product);
        }
    }

    public ProductVO getProductById(String productNumber) throws NotFoundException {
        Product product = repository.findByProductNumber(productNumber);
        if (product == null) {
            throw new NotFoundException();
        } else {
            return convert(product);
        }
    }

    public Page<ProductVO> getAllProducts(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        Page<ProductVO> vos = products.map(product -> convert(product));
        return vos;
    }

    private ProductVO convert(Product entity) {
        ProductVO vo = null;

        if (entity != null) {
            vo = new ProductVO();
            beanCopyProperties(entity, vo);
        }
        return vo;
    }

    private Product convert(ProductVO vo) {
        return assign(new Product(), vo);
    }

    private Product assign(Product entity, ProductVO vo) {
        if (vo != null) {
            beanCopyProperties(vo, entity);
        }
        return entity;
    }

    // NB: Workaround for BeanUtils.copyProperties which copies null values
    private void beanCopyProperties(final Object source, final Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        String properties[] = {"productNumber", "name", "category", "price"};
        Arrays.asList(properties).forEach(propName -> {
            Object value = src.getPropertyValue(propName);
            if (value != null) {
                trg.setPropertyValue(propName, value);
            }
            ;
        });
    }
}
