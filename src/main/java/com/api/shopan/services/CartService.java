package com.api.shopan.services;

import com.api.shopan.dtos.ProductDTO;
import com.api.shopan.dtos.ProductsItemsCartDTO;
import com.api.shopan.entities.Cart;
import com.api.shopan.entities.CartItem;
import com.api.shopan.entities.Product;
import com.api.shopan.entities.User;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.CartItemRepository;
import com.api.shopan.repositories.CartRepository;
import com.api.shopan.repositories.ProductRepository;
import com.api.shopan.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private CartItemRepository cartItemRepository;



    public void addProduct(ProductDTO productDTO, int quantity) throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (cartRepository.findByUserId(authUser.getId()).isEmpty()) {
                this.createCart();
            }
            if (!productsRepository.existsById(HashUtils.decodeBase64ToInt(productDTO.getHashId()))) {
                throw new EmptyException("Produto");
            }

            Cart cart = cartRepository.findByUserId(authUser.getId()).orElse(null);

            saveCartItem(productDTO.parseToObject(), quantity, cart);

            updateCartTotalPrice(cart);

        } catch (EmptyException e) {
            throw e;
        } catch (Exception e){
            throw new Exception(e);
        }

    }


    public void createCart() throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Cart cart = new Cart();
            cart.setUser(authUser);
            cart.setTotalValue(0.00);
            cartRepository.save(cart);

        } catch (Exception e){
            throw new Exception(e);
        }
    }


    private void saveCartItem(Product product, int quantity, Cart cart) throws Exception {
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setSubtotal(item.getQuantity() * item.getProduct().getUnitPrice());
            cartItemRepository.save(item);
        } else {
            if (product == null) {
                throw new EmptyException("Produto");
            }

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSubtotal(product.getUnitPrice());

            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }
    }

    private void updateCartTotalPrice(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        cart.setTotalValue(total);
        cartRepository.save(cart);
    }

    public void removeItemCart(ProductDTO productDTO) throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Cart cart = cartRepository.findByUserId(authUser.getId()).orElse(null);
            List<CartItem> items = cartItemRepository.findByCart(cart);

            if (cart == null) {
                throw new EmptyException("Carrinho");
            }
            if (items.isEmpty()) {
                throw new ListEmptyException("Item do carrinho");
            }
            if (!existProductInCart(cart, productDTO.parseToObject())) {
                throw new EmptyException("Produto");
            }

            this.removeItem(items, productDTO.parseToObject());
            this.updateCartTotalPrice(cart);

        } catch (EmptyException | ListEmptyException e) {
            throw e;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    private void removeItem(List<CartItem> list, Product productDelete) throws Exception {
        list.forEach(item -> {
            if (item.getProduct().getId().equals(productDelete.getId())) {
                cartItemRepository.delete(item);
            }
        });
    }

    public void changeQuantityItemCart(ProductDTO productDTO, Integer newQuantity) throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Cart cart = cartRepository.findByUserId(authUser.getId()).orElse(null);
            List<CartItem> items = cartItemRepository.findByCart(cart);
            if (cart == null) {
                throw new EmptyException("Carrinho");
            }
            items.forEach(item -> {
                if (item.getProduct().equals(productDTO.parseToObject())) {
                    item.setQuantity(newQuantity);
                }
            });
        } catch (EmptyException e) {
            throw e;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<ProductsItemsCartDTO> showCart() throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Cart cart = cartRepository.findByUserId(authUser.getId()).orElse(null);
            List<CartItem> items = cartItemRepository.findByCart(cart);
            List<ProductsItemsCartDTO> productsItemsCartDTOList = new ArrayList<>();
            if (items.isEmpty()) {
                throw new ListEmptyException("Item do carrinho");
            }
            for (CartItem item : items) {
                ProductsItemsCartDTO productsItemsCartDTO = new ProductsItemsCartDTO();
                productsItemsCartDTO.setProductDTO(item.getProduct().parseToDTO());
                productsItemsCartDTO.setQuantity(item.getQuantity());
                productsItemsCartDTO.setSubtotal(item.getSubtotal());
                productsItemsCartDTOList.add(productsItemsCartDTO);
            }

            return productsItemsCartDTOList;
        } catch (ListEmptyException e) {
            throw e;
        } catch(Exception e) {
            throw new Exception(e);
        }
    }

    private boolean existProductInCart(Cart cart, Product product) {
        for (CartItem cartItem : cartItemRepository.findByCart(cart)) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }
}