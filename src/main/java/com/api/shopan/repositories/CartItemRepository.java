package com.api.shopan.repositories;

import com.api.shopan.entities.Cart;
import com.api.shopan.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart(Cart cart);
}
