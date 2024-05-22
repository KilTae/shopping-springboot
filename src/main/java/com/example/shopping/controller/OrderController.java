package com.example.shopping.domain;


import com.example.shopping.controller.req.OrderCreateRequest;
import com.example.shopping.controller.res.OrderPageResponse;
import com.example.shopping.controller.res.OrderResponse;
import com.example.shopping.domain.MerchantId;
import com.example.shopping.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // MerchantID UUID 생성
    @GetMapping("/merchantId")
    @ResponseStatus(HttpStatus.CREATED)
    public MerchantId merchantIdCreate() {
        return MerchantId.builder().merchantId(UUID.randomUUID()).build();
    }

    // 주문 생성
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void orderCreate(@RequestBody @Valid OrderCreateRequest orderCreateRequest) {
        orderService.cartOrder(orderCreateRequest);
    }

    // 주문 전체 조회
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public List<OrderPageResponse> orderFindMember(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return orderService.orderFindMember(pageable);
    }

    // 주문 단건 조회
    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public OrderResponse orderFindOne(@PathVariable("orderId") Long orderId) {
        return orderService.orderFindOne(orderId);
    }
}