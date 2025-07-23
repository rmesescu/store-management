package com.store.management.mapper;

import com.store.management.entity.Product;
import com.store.management.models.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    ProductDTO productToProductDTO(Product product);
    Product productDTOtoProduct(ProductDTO productDTO);

    List<ProductDTO> productToProductDTOList(List<Product> entity);
    List<Product> productDTOtoProductList(List<ProductDTO> dto);
}