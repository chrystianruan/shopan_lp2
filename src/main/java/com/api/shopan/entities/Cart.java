package com.api.shopan.entities;

import com.api.shopan.dtos.CartDTO;
import com.api.shopan.dtos.CartItemDTO;
import com.api.shopan.utils.HashUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items;

    @Column(name = "total_value")
    private Double totalValue;

//    public CartDTO parseToDTO() {
//        CartDTO cartDTO = new CartDTO();
//        cartDTO.setHashId(HashUtils.encodeBase64(id.toString()));
//        cartDTO.setUser(user.parseToDTO());
////        cartDTO.setItems(parseToDTOList());
//        cartDTO.setTotalValue(totalValue);
//
//        return cartDTO;
//    }
//    private List<CartItemDTO> parseToDTOList() {
//        List<CartItemDTO> cartItemsDTO = new ArrayList<>();
//        for (CartItem cartItem : items) {
//            cartItemsDTO.add(cartItem.parseToDTO());
//        }
//
//        return cartItemsDTO;
//    }

}