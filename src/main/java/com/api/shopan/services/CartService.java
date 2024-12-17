package com.api.shopan.services;

import com.api.shopan.entities.Cart;
import com.api.shopan.entities.CartItem;
import com.api.shopan.entities.Product;
import com.api.shopan.repositories.CartRepository;
import com.api.shopan.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productsRepository;

    public void createCart(Long userId) {
        if (cartRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Cart already exists for user ID: " + userId);
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
    }

    public void addProduct(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            item.setSubtotal(item.getQuantity() * item.getProduct().getUnitPrice());
        } else {
            Product product = productsRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            newItem.setSubtotal(product.getUnitPrice());
            cart.getItems().add(newItem);
        }

        updateCartTotalPrice(cart);
        cartRepository.save(cart);
    }

    private void updateCartTotalPrice(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        cart.setTotalPrice(total);
    }
}