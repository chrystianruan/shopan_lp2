package com.api.shopan.controllers;

import com.api.shopan.dtos.OrderItemDTO;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.OrderItemService;
import com.api.shopan.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order_items")
@RequiredArgsConstructor

public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;


    @GetMapping()
    public ResponseEntity<Map<String, ?>> getAll() {
        try {
            List<OrderItemDTO> list = orderItemService.getAll();

            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
        } catch (ListEmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar item do pedido"));
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, String>> create(@RequestBody OrderItemDTO orderItemDTO) {
        try {
            orderItemService.save(orderItemDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build() ;
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar criação de item do pedido"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable String id, @RequestBody OrderItemDTO subjectDTO) {
        try {
            orderItemService.update(id, subjectDTO);
            return ResponseEntity.ok().build();
        } catch (EmptyException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao realizar update de item do pedido"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, ?>> get(@PathVariable String id) {
        try {
            OrderItemDTO subjectDTO = orderItemService.show(id);

            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(subjectDTO));
        } catch (EmptyException emptyException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.makeMessage("Item do pedido não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar item do pedido"));
        }
    }
}
