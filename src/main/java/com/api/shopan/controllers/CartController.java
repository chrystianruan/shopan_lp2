package com.api.shopan.controllers;

import com.api.shopan.dtos.ProductDTO;
import com.api.shopan.dtos.ProductItemCartInDTO;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.CartService;
import com.api.shopan.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-product")
    public ResponseEntity<Map<String, String>> addProductToCart(@RequestBody ProductItemCartInDTO productItemCartInDTO) {
        try {
            cartService.addProduct(productItemCartInDTO.getProductDTO(), productItemCartInDTO.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtils.makeMessage("Product added to the cart successfully."));
        } catch (EmptyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Internal error while creating cart."));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, ?>> getUserCart() {
        try {
            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(cartService.showCart()));
        } catch (ListEmptyException listEmptyException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(listEmptyException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Internal error while fetching cart."));
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<Map<String, String>> deleteProductFromCart(@RequestBody ProductDTO productDTO) {
        try {
            cartService.removeItemCart(productDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseUtils.makeMessage("Product deleted successfully."));
        } catch (ListEmptyException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (EmptyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Internal error while deleting product from cart."));
        }
    }

}