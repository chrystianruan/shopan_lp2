package com.api.shopan.services;

import com.api.shopan.dtos.OrderItemDTO;
import com.api.shopan.entities.OrderItem;
import com.api.shopan.exceptions.AlreadyExistsException;
import com.api.shopan.exceptions.EmptyException;
import com.api.shopan.exceptions.ListEmptyException;
import com.api.shopan.repositories.OrderItemRepository;
import com.api.shopan.utils.HashUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private OrderItemRepository orderItemRepository;
    private final String model = "ItemPedido";

    public void save(OrderItemDTO orderItem) throws AlreadyExistsException {
        if (orderItemRepository.existsById(orderItem.getHashId())) {
            throw new AlreadyExistsException(model);
        }
        OrderItem orderItemEntity = new OrderItem();
        String hashId = orderItem.getHashId();
        orderItemEntity.setId(HashUtils.decodeBase64ToInt(hashId));
        orderItemRepository.save(orderItemEntity);

    }

    public List<OrderItemDTO> getAll() throws ListEmptyException {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        if (orderItems.isEmpty()) {
            throw new ListEmptyException(model);
        }
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO(HashUtils.encodeBase64(orderItem.getId().toString()), orderItem.getQuantity(), orderItem.getOrder(), orderItem.getProduct());
            orderItemDTOS.add(orderItemDTO);
        }
        return orderItemDTOS;
    }

    public OrderItemDTO show(String hashId) throws EmptyException {
        OrderItem orderItem = orderItemRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (orderItem == null) {
            throw new EmptyException(model);
        }
        return orderItem.parseToDTO();
    }


    @Transactional
    public void update(String hashId, OrderItemDTO orderItemDTO) throws AlreadyExistsException, EmptyException {
        OrderItem orderItem = orderItemRepository.findById(HashUtils.decodeBase64ToInt(hashId)).orElse(null);
        if (orderItem == null) {
            throw new EmptyException(model);
        }

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(orderItemDTO.getOrder());
        orderItem.setProduct(orderItemDTO.getProduct());
        orderItemRepository.save(orderItem);
    }

}
