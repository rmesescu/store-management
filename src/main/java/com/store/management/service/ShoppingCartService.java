package com.store.management.service;

import com.store.management.entity.*;
import com.store.management.exception.CartInventoryException;
import com.store.management.exception.CustomerNotFoundException;
import com.store.management.exception.ProductNotFoundException;
import com.store.management.mapper.ShoppingCartMapper;
import com.store.management.models.ShoppingCartDTO;
import com.store.management.repository.CustomerRepository;
import com.store.management.repository.ProductRepository;
import com.store.management.repository.ShoppingCartItemRepository;
import com.store.management.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartService {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository cartItemRepo;
    private final ShoppingCartMapper cartMapper;

    public ShoppingCartDTO getCartByCustomerId(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.BAD_REQUEST));

        ShoppingCart cart = getShoppingCart(customer);

        return cartMapper.toDTO(cart);
    }

    public ShoppingCartDTO addProductToCart(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) throw new CartInventoryException("Quantity must be positive", HttpStatus.BAD_REQUEST);

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.BAD_REQUEST));

        Product product = productRepo.findById(productId)
                .orElseThrow(() ->new ProductNotFoundException("Product not found", HttpStatus.BAD_REQUEST));

        ShoppingCart cart = getShoppingCart(customer);

        Optional<ShoppingCartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        addItemToCard(quantity, existingItemOpt, cart, product);

        shoppingCartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    public ShoppingCartDTO removeProductFromCart(Long customerId, Long productId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.BAD_REQUEST));

        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            throw new CartInventoryException("Shopping cart not found", HttpStatus.BAD_REQUEST);
        }

        ShoppingCartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartInventoryException("Product not in cart", HttpStatus.BAD_REQUEST));

        cart.getItems().remove(itemToRemove);
        cartItemRepo.delete(itemToRemove);
        shoppingCartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public ShoppingCartDTO clearCart(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found", HttpStatus.BAD_REQUEST));

        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) return null;

        cartItemRepo.deleteAll(cart.getItems());
        cart.getItems().clear();

        shoppingCartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    private ShoppingCart getShoppingCart(Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            cart = createCartForCustomer(customer);
        }
        return cart;
    }

    private ShoppingCart createCartForCustomer(Customer customer) {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setCustomer(customer);
        newCart = shoppingCartRepository.save(newCart);
        customer.setShoppingCart(newCart);
        customerRepo.save(customer);
        return newCart;
    }

    private void addItemToCard(int quantity, Optional<ShoppingCartItem> existingItemOpt, ShoppingCart cart, Product product) {
        if (existingItemOpt.isPresent()) {
            ShoppingCartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepo.save(existingItem);
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
            cartItemRepo.save(newItem);
        }
    }
}
