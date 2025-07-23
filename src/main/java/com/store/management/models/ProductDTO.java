package com.store.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String category;
    private String subcategory;
    private String productType;
    private Double price;
    private Integer stockQuantity;
}
