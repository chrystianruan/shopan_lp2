package com.api.shopan.repositories;

import com.api.shopan.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select count(category) > 1 from Category category where category.name ilike concat('%', :name, '%')")
    boolean existsByName(String name);

    @Query("select count(category) > 1 from Category category where category.name ilike concat('%', :name, '%') and category <> ?2")
    boolean existsByNameAndCurrentObjectDifferent(String name, Category category);
}
