package com.api.shopan.repositories;

import com.api.shopan.entities.Category;
import com.api.shopan.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select count(product) > 0 from Product product where product.name ilike concat('%', :name, '%')")
    boolean existsByName(String name);

    @Query("select count(product) > 0 from Product product where product.name ilike concat('%', :name, '%') and product <> :product")
    boolean existsByNameAndCurrentObjectDifferent(String name, Product product);
}
