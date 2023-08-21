package io.github.flea777.products.controllers;

import io.github.flea777.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductRepository repository;

}
