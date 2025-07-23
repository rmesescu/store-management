package com.store.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDTO {
    private Long shoppingCartItemId;
    private ProductDTO product;
    private Integer quantity;
}
