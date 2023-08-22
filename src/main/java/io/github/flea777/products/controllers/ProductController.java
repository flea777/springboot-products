package io.github.flea777.products.controllers;

import io.github.flea777.products.dtos.ProductDTO;
import io.github.flea777.products.models.ProductModel;
import io.github.flea777.products.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> productModel = repository.findById(id);
        return productModel.<ResponseEntity<Object>>map
                (model -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(model))
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invalid product ID"));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductDTO productDTO) {
        Optional<ProductModel> productModel = repository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID");
        }
        var productModel1 = productModel.get();
        BeanUtils.copyProperties(productDTO, productModel1);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel1));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productModel = repository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product ID");
        }
        repository.delete(productModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
