package com.store.management.repository;

import com.store.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findProductByCategory(String category);

    public List<Product> findProductByProductName(String name);
}
