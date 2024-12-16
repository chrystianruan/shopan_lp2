package com.api.shopan.services;

import com.api.shopan.dtos.CategoryDTO;
import com.api.shopan.entities.Category;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.CategoryRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final String model = "Categoria";

    public void save(CategoryDTO category) throws Exception {
        try {
            if (categoryRepository.existsByName(category.getName())) {
                throw new AlreadyExistsException(model);
            }
            Category categoryEntity = new Category();
            categoryEntity.setName(category.getName());
            categoryRepository.save(categoryEntity);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<CategoryDTO> getAll() throws Exception {
        try {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                throw new ListEmptyException(model);
            }
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category category : categories) {
                CategoryDTO categoryDTO = new CategoryDTO(category.getName());
                categoryDTOS.add(categoryDTO);
            }
            return categoryDTOS;
        } catch (Exception e){
            throw new Exception(e);
        }

    }

    public CategoryDTO show(String hashId) throws Exception {
        try {
            Category category = categoryRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElseThrow(() -> new EmptyException(model));
            return category.parseToDTO();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    @Transactional
    public void update(String hashId, CategoryDTO categoryDTO) throws Exception {
        try {
            Category category = categoryRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElseThrow(() -> new EmptyException(model));
            if (categoryRepository.existsByNameAndCurrentObjectDifferent(categoryDTO.getName(), category)) {
                throw new AlreadyExistsException(model);
            }
            category.setName(categoryDTO.getName());
            categoryRepository.save(category);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
