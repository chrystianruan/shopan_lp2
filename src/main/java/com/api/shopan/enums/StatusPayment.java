package com.api.shopan.enums;

public enum StatusPayment {
    APPROVED(1), RECUSED(2);

    private final int value;

    StatusPayment(int value) {
        this.value = value;
    }
}
