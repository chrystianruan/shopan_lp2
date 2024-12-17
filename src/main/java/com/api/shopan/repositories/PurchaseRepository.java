package com.api.shopan.repositories;

import com.api.shopan.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("select purchase from Purchase purchase where purchase.id = ?1")
    boolean existsById(String id);

}
