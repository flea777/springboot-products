package io.github.flea777.products.controllers;

import io.github.flea777.products.dtos.ProductDTO;
import io.github.flea777.products.models.ProductModel;
import io.github.flea777.products.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(productModel));
    }
}
