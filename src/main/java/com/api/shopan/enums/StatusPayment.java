package com.api.shopan.enums;

import lombok.Getter;

@Getter
public enum StatusPayment {
    APPROVED(1), RECUSED(2);

    private int value;

    StatusPayment(int value) {
        this.value = value;
    }

}
