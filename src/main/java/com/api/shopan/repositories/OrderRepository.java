package com.api.shopan.repositories;

import com.api.shopan.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select order from Order order where order.id = ?1")
    boolean existsById(String id);

}
