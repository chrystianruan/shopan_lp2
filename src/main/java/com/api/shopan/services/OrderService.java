package com.api.shopan.services;

import com.api.shopan.dtos.CartDTO;
import com.api.shopan.dtos.CartItemDTO;
import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.entities.Order;
import com.api.shopan.entities.User;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.OrderRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private final String model = "Pedido";

    public void save(CartDTO cartDTO) throws AlreadyExistsException {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Order order = new Order();
            Random random = new Random();
            int randomInt = random.nextInt(2)+1;
            order.setStatus(randomInt);
            order.setUser(authUser);

            orderRepository.save(order);

            addItemsToOrder(cartDTO, order);
            clearCart(cartDTO);

        } catch (AlreadyExistsException e) {

        }


    }

    private void addItemsToOrder(CartDTO cartDTO, Order order) {
        for (CartItemDTO item : cartDTO.getItems()) {

        }
    }

    private void clearCart(CartDTO cartDTO) {

    }

    private void clearItemsCart(CartDTO cartDTO) {

    }

//    public List<OrderDTO> getAll() throws ListEmptyException {
//        List<Order> orders = orderRepository.findAll();
//        if (orders.isEmpty()) {
//            throw new ListEmptyException(model);
//        }
//        List<OrderDTO> orderDTOS = new ArrayList<>();
//        for (Order order : orders) {
//            OrderDTO orderDTO = new OrderDTO(HashUtils.encodeBase64(order.getId().toString()), order.getTotal_value(), order.getStatus(), order.getUser());
//            orderDTOS.add(orderDTO);
//        }
//        return orderDTOS;
//    }
//
//    public OrderDTO show(String hashId) throws EmptyException {
//        Order order = orderRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
//        if (order == null) {
//            throw new EmptyException(model);
//        }
//        return order.parseToDTO();
//    }
//
//
//    @Transactional
//    public void update(String hashId, OrderDTO orderDTO) throws AlreadyExistsException, EmptyException {
//        Order order = orderRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
//        if (order == null) {
//            throw new EmptyException(model);
//        }
//
//        order.setTotal_value(orderDTO.getTotal_value());
//        order.setStatus(orderDTO.getStatus());
//        order.setUser(orderDTO.getUser());
//        orderRepository.save(order);
//    }

}
