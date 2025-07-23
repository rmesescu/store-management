package com.store.management.service;

import com.store.management.entity.Product;
import com.store.management.exception.ProductNotFoundException;
import com.store.management.mapper.ProductMapper;
import com.store.management.models.ProductDTO;
import com.store.management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product(1L, "PHONE", "ELECTRONICS", "MOBILE", "SMARTPHONE", 699.99, 10);
        productDTO = new ProductDTO(1L, "PHONE", "ELECTRONICS", "MOBILE", "SMARTPHONE", 699.99, 10);
    }

    @Test
    void getAllProducts_shouldReturnProductDTOList() {
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productToProductDTOList(products)).thenReturn(List.of(productDTO));

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(productDTO.getProductName(), result.get(0).getProductName());
        verify(productRepository).findAll();
        verify(productMapper).productToProductDTOList(products);
    }

    @Test
    void findByProductName_shouldReturnMatchingProducts() {
        when(productRepository.findProductByProductName("PHONE")).thenReturn(List.of(product));
        when(productMapper.productToProductDTOList(List.of(product))).thenReturn(List.of(productDTO));

        List<ProductDTO> result = productService.findByProductName("PHONE");

        assertEquals(1, result.size());
        assertEquals("PHONE", result.get(0).getProductName());
    }

    @Test
    void findByCategory_shouldReturnProductsInCategory() {
        when(productRepository.findProductByCategory("ELECTRONICS")).thenReturn(List.of(product));
        when(productMapper.productToProductDTOList(List.of(product))).thenReturn(List.of(productDTO));

        List<ProductDTO> result = productService.findByCategory("ELECTRONICS");

        assertEquals(1, result.size());
        assertEquals("ELECTRONICS", result.get(0).getCategory());
    }

    @Test
    void createProduct_shouldSaveAndReturnProductDTO() {
        when(productMapper.productDTOtoProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertEquals(productDTO.getProductName(), result.getProductName());
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_shouldUpdateAndReturnProductDTO() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertEquals(productDTO.getProductName(), result.getProductName());
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_shouldThrowWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                productService.updateProduct(1L, productDTO));

        assertEquals("Product not found ", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_shouldThrowWhenNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                productService.deleteProduct(1L));

        assertEquals("Product not found", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}
