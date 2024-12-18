package com.api.shopan.entities;

import com.api.shopan.dtos.CartItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal = 0.0;

//    public CartItemDTO parseToDTO() {
//        CartItemDTO cartItemDTO = new CartItemDTO();
//        cartItemDTO.setCart(cart.parseToDTO());
//        cartItemDTO.setProduct(product.parseToDTO());
//        cartItemDTO.setQuantity(quantity);
//        cartItemDTO.setSubtotal(subtotal);
//        return cartItemDTO;
//    }

}
