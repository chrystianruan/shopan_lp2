package com.api.shopan.controllers;

import com.api.shopan.dtos.PurchaseDTO;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.PurchaseService;
import com.api.shopan.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor

public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping()
    public ResponseEntity<Map<String, ?>> getAll() {
        try {
            List<PurchaseDTO> list = purchaseService.getAll();

            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
        } catch (ListEmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar compra"));
        }
    }

}
