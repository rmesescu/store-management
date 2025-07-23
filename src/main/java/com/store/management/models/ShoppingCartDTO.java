package com.store.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    private Long shoppingCartId;
    private Long customerId;
    private List<ShoppingCartItemDTO> items;
    private Double total;  // calculated total
}
