package com.api.shopan.controllers;

import com.api.shopan.entities.Cart;
import com.api.shopan.services.CartService;
import com.api.shopan.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Map<String, String>> createCart(@PathVariable Long userId) {
        try {
            cartService.createCart(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtils.makeMessage("Cart created successfully."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Internal error while creating cart."));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, ?>> getCartByUserId(@PathVariable Long userId) {
        try {
            Cart cartDTO = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(cartDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Internal error while fetching cart."));
        }
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<Map<String, String>> addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            cartService.addProduct(userId, productId);
            return ResponseEntity.ok(ResponseUtils.makeMessage("Product added to cart successfully."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Internal error while adding product to cart."));
        }
    }
}