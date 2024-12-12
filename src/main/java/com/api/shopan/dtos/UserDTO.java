package com.api.shopan.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
}
