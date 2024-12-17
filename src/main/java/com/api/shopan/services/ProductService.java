package com.api.shopan.services;

import com.api.shopan.dtos.ProductDTO;
import com.api.shopan.entities.Product;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.CategoryRepository;
import com.api.shopan.repositories.ProductRepository;
import com.api.shopan.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    private final String model = "Produto";
    @Autowired
    private CategoryRepository categoryRepository;

    public void save(ProductDTO productDTO) throws Exception {
        try {
            if (productRepository.existsByName(productDTO.getName())) {
                throw new AlreadyExistsException();
            }
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setUnitPrice(productDTO.getUnitPrice());
            product.setLinkImg(productDTO.getLinkImg());
            product.setCategory(productDTO.getCategory().parseToObject());
            productRepository.save(product);

        } catch (AlreadyExistsException alreadyExistsException) {
            throw new AlreadyExistsException(model);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<ProductDTO> getAllProducts() throws Exception {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new ListEmptyException();
            }
            List<ProductDTO> productDTOs = new ArrayList<>();
            for (Product product : products) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setHashId(HashUtils.encodeBase64(product.getId().toString()));
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setLinkImg(product.getLinkImg());
                productDTO.setUnitPrice(product.getUnitPrice());
                productDTO.setCategory(product.getCategory().parseToDTO());
                productDTOs.add(productDTO);
            }
            return productDTOs;
        } catch (ListEmptyException listEmptyException) {
            throw new ListEmptyException(model);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public ProductDTO getProductByHashId(String hashId) throws Exception {
        try {
            Product product = productRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
            if (product == null) {
                throw new EmptyException();
            }
            return product.parseToDTO();
        } catch (EmptyException emptyException) {
            throw new EmptyException(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductByHashId(String hashId, ProductDTO productDTO) throws Exception {
        try {
            Product product = productRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
             if (product == null) {
                 throw new EmptyException(model);
             }
             if (productRepository.existsByNameAndCurrentObjectDifferent(productDTO.getName(), product)) {
                 throw new AlreadyExistsException();
             }
             if (productDTO.getCategory() != null) {
                 if (!categoryRepository.existsById(HashUtils.decodeBase64ToInt(productDTO.getCategory().getHashId()))) {
                     throw new EmptyException("Categoria");
                 }
             }
             product.setName(productDTO.getName() == null ? product.getName() : productDTO.getName());
             product.setDescription(productDTO.getDescription() == null ? product.getDescription() : productDTO.getDescription());
             product.setLinkImg(productDTO.getLinkImg() == null ? product.getLinkImg() : productDTO.getLinkImg());
             product.setUnitPrice(productDTO.getUnitPrice() == null ? product.getUnitPrice() : productDTO.getUnitPrice());
             product.setCategory(productDTO.getCategory() == null ? product.getCategory() : productDTO.getCategory().parseToObject());
             productRepository.save(product);
        } catch (EmptyException emptyException) {
            throw new EmptyException(emptyException.getMessage());
        } catch (AlreadyExistsException alreadyExistsException) {
            throw new AlreadyExistsException(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
