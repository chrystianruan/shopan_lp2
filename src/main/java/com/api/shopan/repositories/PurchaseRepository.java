package com.api.shopan.repositories;

import com.api.shopan.entities.Purchase;
import com.api.shopan.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("select purchase from Purchase purchase where purchase.id = ?1")
    boolean existsById(String id);

    @Query("select purchase from Purchase purchase inner join Order o on o = purchase.order where o.user = :user")
    List<Purchase> findByUser(User user);

}
