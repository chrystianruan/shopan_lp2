package com.api.shopan.dtos;

import com.api.shopan.entities.Order;
import com.api.shopan.entities.Product;
import com.api.shopan.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemDTO {
    private String hashId;
    private Integer quantity;
    private Order order;
    private Product product;
}
