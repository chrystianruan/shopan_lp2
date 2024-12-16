package com.api.shopan.services;

import com.api.shopan.entities.Product;
import com.api.shopan.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product save(Product product) {
        return null;
    }
}
