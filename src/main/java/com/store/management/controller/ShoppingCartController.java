package com.store.management.controller;

import com.store.management.models.ShoppingCartDTO;
import com.store.management.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController {

    private final ShoppingCartService cartService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ShoppingCartDTO> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }

    @PostMapping("/{customerId}/add/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ShoppingCartDTO> addToCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.addProductToCart(customerId, productId, quantity));
    }

    @DeleteMapping("/{customerId}/remove/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ShoppingCartDTO> removeFromCart(
            @PathVariable Long customerId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeProductFromCart(customerId, productId));
    }

    @DeleteMapping("/{customerId}/clear")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ShoppingCartDTO> clearCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.clearCart(customerId));
    }
}
