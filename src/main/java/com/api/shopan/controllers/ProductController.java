package com.api.shopan.controllers;

import com.api.shopan.dtos.ProductDTO;
import com.api.shopan.entities.Product;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.ProductService;
import com.api.shopan.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/store")
    public ResponseEntity<Map<String, String>> saveProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.save(productDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtils.makeMessage("Product saved successfully"));

        } catch (AlreadyExistsException alreadyExistsException) {
            return new ResponseEntity<>(ResponseUtils.makeMessage("Product already exists"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseUtils.makeMessage("Product not saved"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, ?>> getAllProducts() {
        try {
            return ResponseEntity.ok().body(ResponseUtils.makeMessageWithList(productService.getAllProducts()));
        } catch (ListEmptyException listEmptyException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(listEmptyException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage(e.getMessage()));
        }
    }
    @GetMapping("/{hashId}")
    public ResponseEntity<Map<String, ?>> getProduct(@PathVariable String hashId) {
        try {
            return ResponseEntity.ok().body(ResponseUtils.makeMessageWithObject(productService.getProductByHashId(hashId)));
        } catch (EmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Internal server error"));
        }
    }

    @PutMapping("/update/{hashId}")
    public ResponseEntity<Map<String, ?>> updateProduct(@PathVariable String hashId, @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProductByHashId(hashId, productDTO);

            return ResponseEntity.ok().build();
        } catch (EmptyException emptyException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(emptyException.getMessage()));
        } catch (AlreadyExistsException alreadyExistsException) {
            return new ResponseEntity<>(ResponseUtils.makeMessage("Product updated successfully"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage(e.getMessage()));
        }
    }
}
