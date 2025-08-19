package com.ecom.order.service;

import com.ecom.order.clients.ProductServiceClient;
import com.ecom.order.clients.UserServiceClient;
import com.ecom.order.dto.CartItemRequest;
import com.ecom.order.dto.ProductResponse;
import com.ecom.order.dto.UserResponse;
import com.ecom.order.model.CartItem;
import com.ecom.order.repository.CartItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;


    public boolean addToCart(String userId, CartItemRequest request) {
        ProductResponse product = productServiceClient.getProductById(request.getProductId());
        if (product == null) {
            return false;
        }

        if(product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        UserResponse userResponse = userServiceClient.getUserById(userId);
        if (userResponse == null) {
            return false;
        }

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, Long.valueOf(request.getProductId()));
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1.00));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(Long.valueOf(request.getProductId()));
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1));
            cartItemRepository.save(cartItem);
        }
        return true;
    }


    public boolean deleteItemFromCart(String userId, Long productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
            if(existingCartItem != null) {
                cartItemRepository.delete(cartItem);
                return true;
            }
        }

        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
