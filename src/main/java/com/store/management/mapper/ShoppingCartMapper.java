package com.store.management.mapper;

import com.store.management.entity.ShoppingCart;
import com.store.management.models.ShoppingCartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ShoppingCartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "total", expression = "java(shoppingCart.getTotal())")
    ShoppingCartDTO toDTO(ShoppingCart shoppingCart);

    ShoppingCart toEntity(ShoppingCartDTO dto);
}

