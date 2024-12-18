package com.api.shopan.services;

import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.dtos.PurchaseDTO;
import com.api.shopan.entities.Order;
import com.api.shopan.entities.Purchase;
import com.api.shopan.entities.User;
import com.api.shopan.enums.PaymentMethodEnum;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.PurchaseRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    private final String model = "Compra";

    public void save(Order order, PaymentMethodEnum paymentMethodEnum) {
        Purchase purchase = new Purchase();
        purchase.setPaymentMethod(paymentMethodEnum.toString());
        purchase.setOrder(order);
        purchaseRepository.save(purchase);
    }

    public List<PurchaseDTO> getAll() throws ListEmptyException {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Purchase> purchases = purchaseRepository.findByUser(authUser);
        if (purchases.isEmpty()) {
            throw new ListEmptyException(model);
        }
        List<PurchaseDTO> purchasesDTOS = new ArrayList<>();
        for (Purchase purchase : purchases) {
            PurchaseDTO purchaseDTO = new PurchaseDTO(HashUtils.encodeBase64(purchase.getId().toString()), purchase.getPaymentMethod(), purchase.getOrder().parseToDTO());
            purchasesDTOS.add(purchaseDTO);
        }
        return purchasesDTOS;
    }


}
