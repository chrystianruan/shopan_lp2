package com.api.shopan.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
    CARTAO(1), PIX(2), BOLETO(3), ESPECIE(4);

    private int value;

    PaymentMethodEnum(int value) {
        this.value = value;
    }

    public static PaymentMethodEnum fromValue(int valor) {
        for (PaymentMethodEnum type : PaymentMethodEnum.values()) {
            if (type.getValue() == valor) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + valor);
    }
}
