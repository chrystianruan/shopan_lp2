package com.api.shopan.repositories;

import com.api.shopan.entities.Order;
import com.api.shopan.entities.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select order from Order order where order.user = :user")
    List<Order> findAllByUserId(User user);


}
