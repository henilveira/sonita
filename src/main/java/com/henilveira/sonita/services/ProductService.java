package com.henilveira.sonita.services;

import com.henilveira.sonita.domain.product.Product;
import com.henilveira.sonita.domain.product.ProductDTO;
import com.henilveira.sonita.domain.user.User;
import com.henilveira.sonita.repositories.ProductRepository;
import com.henilveira.sonita.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        List<Product> products = repository.findAll();
        return products;
    }

    public Product save(ProductDTO dto) {
        var product = new Product();

        // Copiando as propriedades do DTO e colando na product para nao usar um objeto de tipo entidade para transicionar dados na API
        BeanUtils.copyProperties(dto, product );

        return product;
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
