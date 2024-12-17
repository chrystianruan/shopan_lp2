package com.api.shopan.entities;

import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.dtos.PurchaseDTO;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "total_value")
    private Double totalValue;
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OrderDTO parseToDTO() {
        return new OrderDTO(
            HashUtils.encodeBase64(this.getId().toString()),
            this.getTotalValue(),
            this.getStatus(),
            this.getUser().parseToDTO()
        );
    }
}
