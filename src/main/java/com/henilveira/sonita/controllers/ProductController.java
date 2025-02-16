package com.henilveira.sonita.controllers;

import com.henilveira.sonita.domain.product.ProductDTO;
import com.henilveira.sonita.domain.product.Product;
import com.henilveira.sonita.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @GetMapping("/getall")
    public ResponseEntity getProducts() {
        List<Product> listProducts = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    @PostMapping("/create")
    public ResponseEntity saveProduct(@RequestBody ProductDTO dto) {

        // Instanciando novo objeto da classe Product
        var product = new Product();

        // Copiando as propriedades do DTO e colando na product para nao usar um objeto de tipo entidade para transicionar dados na API
        BeanUtils.copyProperties(dto, product );
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
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
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Produto deletado com sucesso!");
    }
}
