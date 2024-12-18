package com.api.shopan.services;

import com.api.shopan.dtos.CartDTO;
import com.api.shopan.dtos.CartItemDTO;
import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.dtos.OrderItemDTO;
import com.api.shopan.entities.*;
import com.api.shopan.enums.PaymentMethodEnum;
import com.api.shopan.enums.StatusPayment;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.exceptions.RegraDeNegocioException;
import com.api.shopan.repositories.CartItemRepository;
import com.api.shopan.repositories.CartRepository;
import com.api.shopan.repositories.OrderItemRepository;
import com.api.shopan.repositories.OrderRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private PurchaseService purchaseService;
    private final String model = "Pedido";

    public void save(CartDTO cartDTO) throws Exception {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Order order = new Order();
            Random random = new Random();
            int randomStatus = random.nextInt(2)+1;

            order.setStatus(randomStatus);
            order.setUser(authUser);
            order.setTotalValue(cartDTO.getTotalValue());

            orderRepository.save(order);
            int randomPayment = random.nextInt(3)+1;

            if (randomStatus == StatusPayment.APPROVED.getValue()) {
                purchaseService.save(order, PaymentMethodEnum.fromValue(randomPayment));
            }

            addItemsToOrder(cartDTO, order);
            clearCart(cartDTO);

        } catch (EmptyException | ListEmptyException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void addItemsToOrder(CartDTO cartDTO, Order order) throws Exception {
        try {
            for (CartItemDTO item : cartDTO.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(item.getProduct().parseToObject());
                orderItem.setQuantity(item.getQuantity());
                orderItemRepository.save(orderItem);
            }
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    private void clearCart(CartDTO cartDTO) throws Exception {
        Cart cart = cartRepository.findById(HashUtils.decodeBase64ToInt(cartDTO.getHashId())).orElse(null);
        if (cart == null) {
            throw new EmptyException("Carrinho");
        }
        clearItemsCart(cart);
        cartRepository.delete(cart);
    }

    private void clearItemsCart(Cart cart) throws ListEmptyException{
        if (cart.getItems().isEmpty()) {
            throw new ListEmptyException("Itens do carrinho");
        }
        cartItemRepository.deleteAll(cartItemRepository.findByCart(cart));
    }

    public List<OrderDTO> getAllOfUser() throws Exception {
        try {
            List<Order> orders = orderRepository.findAllByUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            if (orders.isEmpty()) {
                throw new ListEmptyException(model);
            }
            List<OrderDTO> orderDTOS = new ArrayList<>();
            for (Order order : orders) {
                OrderDTO orderDTO = new OrderDTO(HashUtils.encodeBase64(order.getId().toString()), order.getTotalValue(), order.getStatus(), order.parseToDTO().getUser());
                orderDTOS.add(orderDTO);
            }
            return orderDTOS;
        } catch (ListEmptyException e) {
          throw e;
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public OrderDTO show(String hashId) throws RegraDeNegocioException, EmptyException {
        Order order = orderRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (order == null) {
            throw new EmptyException(model);
        }
        if (!Objects.equals(order.getUser().getId(), authUser.getId())) {
            throw new RegraDeNegocioException("Pedido não pertence ao usuário logado");
        }
        return order.parseToDTO();
    }



}
