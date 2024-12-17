package com.api.shopan.dtos;

import com.api.shopan.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsItemsCartDTO {
    @JsonProperty("product")
    private ProductDTO productDTO;
    private int quantity;
    private double subtotal;
}
