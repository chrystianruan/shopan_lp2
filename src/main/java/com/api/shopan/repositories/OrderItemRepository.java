package com.api.shopan.repositories;

import com.api.shopan.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query("select orderItem from OrderItem orderItem where orderItem.id = ?1")
    boolean existsById(String id);

}
