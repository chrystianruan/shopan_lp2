package com.api.shopan.controllers;

import com.api.shopan.dtos.CategoryDTO;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.services.CategoryService;
import com.api.shopan.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<Map<String, ?>> getAll() {
        try {
            List<CategoryDTO> list = categoryService.getAll();

            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
        } catch (ListEmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar categorias"));
        }
    }

    @PostMapping("/store")
    public ResponseEntity<Map<String, String>> create(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.save(categoryDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build() ;
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar cadastro de autor"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable String id, @RequestBody CategoryDTO subjectDTO) {
        try {
            categoryService.update(id, subjectDTO);
            return ResponseEntity.ok().build();
        } catch (EmptyException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao realizar update de autor"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, ?>> get(@PathVariable String id) {
        try {
            CategoryDTO subjectDTO = categoryService.show(id);

            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(subjectDTO));
        } catch (EmptyException emptyException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.makeMessage("Categoria não encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar categoria"));
        }
    }
}
