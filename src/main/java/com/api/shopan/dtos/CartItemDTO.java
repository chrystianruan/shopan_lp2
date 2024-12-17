package com.api.shopan.dtos;

import com.api.shopan.entities.CartItem;
import com.api.shopan.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private ProductDTO product;
    private CartDTO cart;
    private Integer quantity;
    private Double subtotal;

    public CartItem parseToObject() {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product.parseToObject());
        cartItem.setCart(cart.parseToObject());
        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(subtotal);
        return cartItem;
    }
}
