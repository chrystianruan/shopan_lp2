package com.api.shopan.controllers;

import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.OrderService;
import com.api.shopan.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping()
    public ResponseEntity<Map<String, ?>> getAll() {
        try {
            List<OrderDTO> list = orderService.getAll();

            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
        } catch (ListEmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar pedidos"));
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, String>> create(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.save(orderDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build() ;
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar criação de pedido"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable String id, @RequestBody OrderDTO subjectDTO) {
        try {
            orderService.update(id, subjectDTO);
            return ResponseEntity.ok().build();
        } catch (EmptyException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao realizar update de pedido"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, ?>> get(@PathVariable String id) {
        try {
            OrderDTO subjectDTO = orderService.show(id);

            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(subjectDTO));
        } catch (EmptyException emptyException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.makeMessage("Pedido não encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar pedido"));
        }
    }
}
