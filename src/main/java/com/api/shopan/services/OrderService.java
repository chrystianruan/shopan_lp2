package com.api.shopan.services;

import com.api.shopan.dtos.OrderDTO;
import com.api.shopan.entities.Order;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.OrderRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private final String model = "Pedido";

    public void save(OrderDTO order) throws AlreadyExistsException {
        if (orderRepository.existsById(order.getHashId())) {
            throw new AlreadyExistsException(model);
        }
        Order orderEntity = new Order();
        String hashId = order.getHashId();
        orderEntity.setId(HashUtils.decodeBase64ToInt(hashId));
        orderRepository.save(orderEntity);

    }

    public List<OrderDTO> getAll() throws ListEmptyException {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ListEmptyException(model);
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(HashUtils.encodeBase64(order.getId().toString()), order.getTotal_value(), order.getStatus(), order.getUser());
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public OrderDTO show(String hashId) throws EmptyException {
        Order order = orderRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (order == null) {
            throw new EmptyException(model);
        }
        return order.parseToDTO();
    }


    @Transactional
    public void update(String hashId, OrderDTO orderDTO) throws AlreadyExistsException, EmptyException {
        Order order = orderRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (order == null) {
            throw new EmptyException(model);
        }

        order.setTotal_value(orderDTO.getTotal_value());
        order.setStatus(orderDTO.getStatus());
        order.setUser(orderDTO.getUser());
        orderRepository.save(order);
    }

}
