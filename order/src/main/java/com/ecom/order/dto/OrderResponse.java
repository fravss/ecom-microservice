package com.ecom.order.dto;

import com.ecom.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;


}
