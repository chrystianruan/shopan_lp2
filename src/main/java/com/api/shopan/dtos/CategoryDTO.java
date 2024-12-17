package com.api.shopan.dtos;

import com.api.shopan.entities.Category;
import com.api.shopan.utils.HashUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String hashId;
    private String name;

    public Category parseToObject() {
        return new Category(HashUtils.decodeBase64ToInt(this.hashId), this.name);
    }
}
