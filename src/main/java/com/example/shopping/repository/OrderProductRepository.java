package com.example.shopping.repository;

import com.example.shopping.domain.Order;
import com.example.shopping.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrder(Order order);
}
