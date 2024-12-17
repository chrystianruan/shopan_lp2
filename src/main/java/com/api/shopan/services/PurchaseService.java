package com.api.shopan.services;

import com.api.shopan.dtos.PurchaseDTO;
import com.api.shopan.entities.Purchase;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.PurchaseRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {
    private PurchaseRepository purchaseRepository;
    private final String model = "Compra";

    public void save(PurchaseDTO purchase) throws AlreadyExistsException {
        if (purchaseRepository.existsById(purchase.getHashId())) {
            throw new AlreadyExistsException(model);
        }
        Purchase purchaseEntity = new Purchase();
        String hashId = purchase.getHashId();
        purchaseEntity.setId(HashUtils.decodeBase64ToInt(hashId));
        purchaseRepository.save(purchaseEntity);

    }

    public List<PurchaseDTO> getAll() throws ListEmptyException {
        List<Purchase> purchases = purchaseRepository.findAll();
        if (purchases.isEmpty()) {
            throw new ListEmptyException(model);
        }
        List<PurchaseDTO> purchasesDTOS = new ArrayList<>();
        for (Purchase purchase : purchases) {
            PurchaseDTO purchaseDTO = new PurchaseDTO(HashUtils.encodeBase64(purchase.getId().toString()), purchase.getPayment_method(), purchase.getOrder());
            purchasesDTOS.add(purchaseDTO);
        }
        return purchasesDTOS;
    }

    public PurchaseDTO show(String hashId) throws EmptyException {
        Purchase purchase = purchaseRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (purchase == null) {
            throw new EmptyException(model);
        }
        return purchase.parseToDTO();
    }


    @Transactional
    public void update(String hashId, PurchaseDTO purchaseDTO) throws AlreadyExistsException, EmptyException {
        Purchase purchase = purchaseRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (purchase == null) {
            throw new EmptyException(model);
        }

        purchase.setPayment_method(purchaseDTO.getPaymentMethod());
        purchase.setOrder(purchaseDTO.getOrder());

        purchaseRepository.save(purchase);
    }

}
