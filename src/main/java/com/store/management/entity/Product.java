package com.store.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    @Column(name = "id")
    private Long productId;
    private String productName;
    private String category;
    private String subcategory;
    private String productType;
    private Double price;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

}
