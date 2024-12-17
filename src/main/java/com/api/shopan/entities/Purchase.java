package com.api.shopan.entities;

import com.api.shopan.dtos.PurchaseDTO;
import com.api.shopan.utils.HashUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_method")
    private String PaymentMethod;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public PurchaseDTO parseToDTO() {
        return new PurchaseDTO(
            HashUtils.encodeBase64(this.getId().toString()),
            this.getPaymentMethod(),
            this.getOrder()
        );
    }
}
