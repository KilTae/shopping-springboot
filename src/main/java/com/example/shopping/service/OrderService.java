package com.example.shopping.service;

import com.example.shopping.controller.req.OrderCreateRequest;
import com.example.shopping.controller.res.OrderPageResponse;
import com.example.shopping.controller.res.OrderResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderService {
    void cartOrder(OrderCreateRequest orderCreateRequest);

    // 주문 조회
    List<OrderPageResponse> orderFindMember(Pageable pageable);

    OrderResponse orderFindOne(Long orderId);
}
