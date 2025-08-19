package com.ecom.order.service;

import com.ecom.order.clients.UserServiceClient;

import com.ecom.order.dto.UserResponse;

import com.ecom.order.dto.OrderItemDTO;
import com.ecom.order.dto.OrderResponse;
import com.ecom.order.model.OrderItem;
import com.ecom.order.model.OrderStatus;
import com.ecom.order.repository.OrderRepository;
import com.ecom.order.model.CartItem;
import com.ecom.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserServiceClient userServiceClient;

    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()){
            return Optional.empty();
        }

        UserResponse userResponse = userServiceClient.getUserById(userId);

        if (userResponse == null){
            return Optional.empty();
        }

        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);
        
        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                String.valueOf(orderItem.getProductId()),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}
