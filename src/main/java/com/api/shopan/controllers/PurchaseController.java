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

//
//    @GetMapping()
//    public ResponseEntity<Map<String, ?>> getAll() {
//        try {
//            List<PurchaseDTO> list = purchaseService.getAll();
//
//            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
//        } catch (ListEmptyException emptyException) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar compra"));
//        }
//    }

//    @PostMapping("/store")
//    public ResponseEntity<Map<String, String>> create(@RequestBody PurchaseDTO purchaseDTO) {
//        try {
//            purchaseService.save(purchaseDTO);
//
//            return ResponseEntity.status(HttpStatus.CREATED).build() ;
//        } catch (AlreadyExistsException alreadyExistsException) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar criação de compra"));
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Map<String, ?>> get(@PathVariable String id) {
//        try {
//            PurchaseDTO subjectDTO = purchaseService.show(id);
//
//            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(subjectDTO));
//        } catch (EmptyException emptyException) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.makeMessage("Compra não encontrada"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar compra"));
//        }
//    }
}
