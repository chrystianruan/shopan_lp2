package com.api.shopan.dtos;

import com.api.shopan.entities.Product;
import com.api.shopan.utils.HashUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String hashId;
    private String name;
    private String description;
    @JsonProperty("unit_price")
    private Double unitPrice;
    @JsonProperty("link_img")
    private String linkImg;
    private CategoryDTO category;

    public Product parseToObject() {
        Product product = new Product();
        product.setId(HashUtils.decodeBase64ToInt(this.hashId));
        product.setName(this.name);
        product.setDescription(this.description);
        product.setLinkImg(this.linkImg);
        product.setUnitPrice(this.unitPrice);
        product.setCategory(this.category.parseToObject());

        return product;
    }

}
