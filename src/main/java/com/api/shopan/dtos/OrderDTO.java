package com.api.shopan.dtos;

import com.api.shopan.entities.Order;
import com.api.shopan.entities.User;
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

public class OrderDTO {
    private String hashId;
    @JsonProperty("total_value")
    private Double totalValue;
    private Integer status;
    private UserDTO user;
}
