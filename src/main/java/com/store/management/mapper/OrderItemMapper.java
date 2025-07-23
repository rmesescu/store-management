package com.store.management.mapper;

import com.store.management.entity.OrderItem;
import com.store.management.models.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);
    OrderItem toEntity(OrderItemDTO dto);
}

