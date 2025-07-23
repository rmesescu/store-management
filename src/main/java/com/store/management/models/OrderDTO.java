package com.store.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private List<OrderItemDTO> items;
    private LocalDateTime placedAt;
    private Double total;  // calculated total
}

