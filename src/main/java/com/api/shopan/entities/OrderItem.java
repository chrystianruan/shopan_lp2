package com.api.shopan.entities;

import com.api.shopan.dtos.OrderItemDTO;
import com.api.shopan.utils.HashUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItemDTO parseToDTO() {
        return new OrderItemDTO(
            HashUtils.encodeBase64(this.getId().toString()),
            this.getQuantity(),
            this.getOrder(),
            this.getProduct()
        );
    }
}
