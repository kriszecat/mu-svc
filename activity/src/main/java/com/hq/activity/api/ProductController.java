package com.hq.activity.api;


import com.hq.activity.exception.ErrorInformation;
import com.hq.activity.exception.NotFoundException;
import com.hq.activity.sample.bean.ProductVO;
import com.hq.activity.sample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@ExposesResourceFor(ProductVO.class)
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping(produces = {APPLICATION_JSON_VALUE})
public class ProductController {

    @Autowired
    ProductService service;

    private Resource<ProductVO> toResource(ProductVO vo) {
        Link link = linkTo(ProductController.class).slash("/products").slash(vo.getProductNumber()).withSelfRel();
        return new Resource<ProductVO>(vo, link.expand(vo.getProductNumber()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorInformation> dataAccessExceptionHandler(HttpServletRequest req, Exception ex) {
        ErrorInformation error = new ErrorInformation(ex.getLocalizedMessage(), req.getRequestURI());
        return new ResponseEntity<ErrorInformation>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInformation> notFoundExceptionHandler(HttpServletRequest req, Exception ex) {
        ErrorInformation error = new ErrorInformation(ex.getLocalizedMessage(), req.getRequestURI());
        return new ResponseEntity<ErrorInformation>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST, consumes = {APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductVO> createProduct(@RequestBody ProductVO vo) {
        ProductVO createdVo = service.createProduct(vo);
        return new ResponseEntity<ProductVO>(createdVo, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/products/wlinks", method = RequestMethod.POST, consumes = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Resource<ProductVO>> createProductWithLinks(@RequestBody ProductVO vo) {
        ProductVO createdVo = service.createProduct(vo);
        return new ResponseEntity<Resource<ProductVO>>(toResource(createdVo), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, consumes = {APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductVO> updateProduct(@PathVariable String id, @RequestBody ProductVO vo) throws NotFoundException {
        vo.setProductNumber(id);
        ProductVO updatedVo = service.updateProduct(vo);
        return new ResponseEntity<ProductVO>(updatedVo, HttpStatus.ACCEPTED);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductVO> deleteProduct(@PathVariable String id) throws NotFoundException {
        ProductVO vo = service.deleteProduct(id);
        return new ResponseEntity<ProductVO>(vo, HttpStatus.ACCEPTED);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductVO> getProduct(@PathVariable String id) throws NotFoundException {
        ProductVO vo = service.getProductById(id);
        return new ResponseEntity<ProductVO>(vo, HttpStatus.FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Page<ProductVO>> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "name") String sort) {
        Pageable pageable = new PageRequest(page, size, new Sort(sort.split(",")));
        Page<ProductVO> pageResult = service.getAllProducts(pageable);
        return new ResponseEntity<Page<ProductVO>>(pageResult, HttpStatus.FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/products/wlinks", method = RequestMethod.GET)
    public ResponseEntity<PageResource<ProductVO>> getAllProductsWithLinks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "name") String sort) {
        Pageable pageable = new PageRequest(page, size, new Sort(sort.split(",")));
        Page<ProductVO> pageResult = service.getAllProducts(pageable);
        return new ResponseEntity<PageResource<ProductVO>>(new PageResource<ProductVO>(pageResult, "page", "size"), HttpStatus.FOUND);
    }

}
