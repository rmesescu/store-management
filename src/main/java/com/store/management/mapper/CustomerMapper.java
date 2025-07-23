package com.store.management.mapper;

import com.store.management.entity.Customer;
import com.store.management.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ShoppingCartMapper.class})
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO dto);
}
