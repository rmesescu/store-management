package com.store.management.service;

import com.store.management.entity.*;
import com.store.management.exception.BadOrderException;
import com.store.management.exception.CartInventoryException;
import com.store.management.exception.CustomerNotFoundException;
import com.store.management.mapper.OrderMapper;
import com.store.management.models.OrderDTO;
import com.store.management.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final CustomerRepository customerRepo;
    private final ShoppingCartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;
    private final ShoppingCartItemRepository cartItemRepo;

    public OrderDTO placeOrder(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.BAD_REQUEST));

        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartInventoryException("Shopping cart is empty", HttpStatus.BAD_REQUEST);
        }

        OrderEntity orderEntity = createOrder(customer, cart);

        cartItemRepo.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepo.save(cart);

        return orderMapper.toDTO(orderEntity);
    }

    public OrderDTO getOrder(Long orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId)
                .orElseThrow(() -> new BadOrderException("Order not found", HttpStatus.BAD_REQUEST));

        return orderMapper.toDTO(orderEntity);
    }

    private OrderEntity createOrder(Customer customer, ShoppingCart cart) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomer(customer);
        orderEntity.setPlacedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        addItemsToOrder(cart, orderEntity, orderItems);

        orderEntity.setItems(orderItems);
        orderRepo.save(orderEntity);
        return orderEntity;
    }

    private static void addItemsToOrder(ShoppingCart cart, OrderEntity orderEntity, List<OrderItem> orderItems) {
        for (ShoppingCartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderEntity(orderEntity);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            removeQuantityFromProductStock(cartItem);
            orderItems.add(orderItem);
        }
    }

    private static void removeQuantityFromProductStock(ShoppingCartItem cartItem) {
        Product product = cartItem.getProduct();
        int newStock = product.getStockQuantity() - cartItem.getQuantity();
        if (newStock < 0) {
            throw new CartInventoryException("Not enough stock for product: " + product.getProductName(), HttpStatus.BAD_REQUEST);
        }
        product.setStockQuantity(newStock);
    }
}

