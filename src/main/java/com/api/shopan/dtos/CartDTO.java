package com.api.shopan.dtos;

import com.api.shopan.entities.Cart;
import com.api.shopan.entities.CartItem;
import com.api.shopan.utils.HashUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String hashId;
    private UserDTO user;
    private List<CartItemDTO> items;
    @JsonProperty("total_value")
    private Double totalValue;

    public Cart parseToObject() {
        Cart cart = new Cart();
        cart.setId(HashUtils.decodeBase64ToInt(hashId));
        cart.setUser(user.parseToObject());
        cart.setTotalValue(totalValue);
        cart.setItems(parseToObjectList());

        return cart;
    }

    private List<CartItem> parseToObjectList() {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDTO cartItemDTO : items) {
            cartItems.add(cartItemDTO.parseToObject());
        }
        return cartItems;
    }
}


