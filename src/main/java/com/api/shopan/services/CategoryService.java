package com.api.shopan.services;

import com.api.shopan.dtos.CategoryDTO;
import com.api.shopan.entities.Category;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.CategoryRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final String model = "Categoria";

    public void save(CategoryDTO category) throws AlreadyExistsException {
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyExistsException(model);
        }
        Category categoryEntity = new Category();
        categoryEntity.setName(category.getName());
        categoryRepository.save(categoryEntity);

    }

    public List<CategoryDTO> getAll() throws ListEmptyException {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ListEmptyException(model);
        }
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO(HashUtils.encodeBase64(category.getId().toString()), category.getName());
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    public CategoryDTO show(String hashId) throws EmptyException {
        Category category = categoryRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (category == null) {
            throw new EmptyException(model);
        }
        return category.parseToDTO();
    }


    @Transactional
    public void update(String hashId, CategoryDTO categoryDTO) throws AlreadyExistsException, EmptyException {
        Category category = categoryRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (category == null) {
            throw new EmptyException(model);
        }
        if (categoryRepository.existsByNameAndCurrentObjectDifferent(categoryDTO.getName(), category)) {
            throw new AlreadyExistsException(model);
        }
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }


}
