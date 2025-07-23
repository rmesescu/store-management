package com.store.management.mapper;

import com.store.management.entity.ShoppingCartItem;
import com.store.management.models.ShoppingCartItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ShoppingCartItemMapper {
    ShoppingCartItemDTO toDTO(ShoppingCartItem item);
    ShoppingCartItem toEntity(ShoppingCartItemDTO dto);
}