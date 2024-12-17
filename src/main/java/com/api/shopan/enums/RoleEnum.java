package com.api.shopan.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(1), GENERAL(2);

    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }
}