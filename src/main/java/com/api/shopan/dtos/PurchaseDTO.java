package com.api.shopan.dtos;

import com.api.shopan.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseDTO {
    private String hashId;
    private String paymentMethod;
    private Order order;
}
