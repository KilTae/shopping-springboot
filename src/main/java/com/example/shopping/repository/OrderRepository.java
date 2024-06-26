package com.example.shopping.repository;

import com.example.shopping.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByMerchantId(String merchantId);

    Optional<Order> findByIdAndMemberId(Long orderId, Long memberId);

}
