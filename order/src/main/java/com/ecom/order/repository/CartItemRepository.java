package com.ecom.order.repository;

import com.ecom.order.model.CartItem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByUserIdAndProductId(String userId, Long productId);

    void deleteByUserIdAndProductId(String userId, Long productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);
}
