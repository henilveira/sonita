package com.henilveira.sonita.controllers;

import com.henilveira.sonita.domain.product.ProductDTO;
import com.henilveira.sonita.domain.product.Product;
import com.henilveira.sonita.repositories.ProductRepository;
import com.henilveira.sonita.services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @Autowired
    ProductService service;

    @GetMapping("/getall")
    public ResponseEntity getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity saveProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping()
    public ResponseEntity findProduct(@RequestParam(required = false) UUID id,
                                      @RequestParam(required = false) String name) {
        if (id != null) {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findByName(name));

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Produto deletado com sucesso!");
    }
}
