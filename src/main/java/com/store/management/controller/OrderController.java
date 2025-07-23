package com.store.management.controller;

import com.store.management.models.OrderDTO;
import com.store.management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{customerId}/place")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long customerId) {
        OrderDTO order = orderService.placeOrder(customerId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        OrderDTO order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }
}

