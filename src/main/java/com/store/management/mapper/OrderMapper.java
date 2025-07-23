package com.store.management.mapper;

import com.store.management.entity.OrderEntity;
import com.store.management.models.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "total", expression = "java(orderEntity.getTotal())")
    OrderDTO toDTO(OrderEntity orderEntity);

    OrderEntity toEntity(OrderDTO dto);
}
