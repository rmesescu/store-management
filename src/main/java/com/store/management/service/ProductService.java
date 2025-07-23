package com.store.management.service;

import com.store.management.entity.Product;
import com.store.management.exception.ProductNotFoundException;
import com.store.management.mapper.ProductMapper;
import com.store.management.models.ProductDTO;
import com.store.management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // 1. Find all products
    public List<ProductDTO> getAllProducts() {
        return productMapper.productToProductDTOList(productRepository.findAll());
    }

    // 2. Find by product name (assuming name is unique, else return list)
    public List<ProductDTO> findByProductName(String productName) {
        List<Product> products = productRepository.findProductByProductName(productName.toUpperCase());
        return productMapper.productToProductDTOList(products);
    }

    // 3. Find by category
    public List<ProductDTO> findByCategory(String category) {
        List<Product> products = productRepository.findProductByCategory(category.toUpperCase());
        return productMapper.productToProductDTOList(products);
    }

    // 4. Create a new product
    public ProductDTO createProduct(ProductDTO productDTO) {
        productDTO.setProductName(productDTO.getProductName().toUpperCase());
        productDTO.setCategory(productDTO.getCategory().toUpperCase());
        productDTO.setProductType(productDTO.getProductType().toUpperCase());
        productDTO.setSubcategory(productDTO.getSubcategory().toUpperCase());
        Product product = productMapper.productDTOtoProduct(productDTO);
        Product saved = productRepository.save(product);
        return productMapper.productToProductDTO(saved);
    }

    // 5. Update an existing product (by id)
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Update fields
                    existingProduct.setProductName(productDTO.getProductName().toUpperCase());
                    existingProduct.setCategory(productDTO.getCategory().toUpperCase());
                    existingProduct.setSubcategory(productDTO.getSubcategory().toUpperCase());
                    existingProduct.setProductType(productDTO.getProductType().toUpperCase());
                    existingProduct.setPrice(productDTO.getPrice());

                    Product updated = productRepository.save(existingProduct);
                    return productMapper.productToProductDTO(updated);
                }).orElseThrow(() -> new ProductNotFoundException("Product not found ", HttpStatus.NOT_FOUND));
    }

    // 6. Delete a product by id
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}
