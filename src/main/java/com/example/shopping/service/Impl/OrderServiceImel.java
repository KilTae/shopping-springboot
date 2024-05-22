package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.OrderCreateRequest;
import com.example.shopping.controller.res.OrderPageResponse;
import com.example.shopping.controller.res.OrderResponse;
import com.example.shopping.domain.*;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.*;
import com.example.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopping.global.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImel implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Override
    public void cartOrder(OrderCreateRequest orderCreateRequest) {
        Member member = getMember();
        Order order = Order.toOrder(orderCreateRequest, member);
        for(OrderCreateRequest.orderProductCreate orderProductCreate : orderCreateRequest.getOrderProductCreates()) {
            Product product = productRepository.findById(orderProductCreate.getProductId()).orElseThrow(
                    () -> new BusinessException(NOT_FOUND_GOODS)
            );


            OrderProduct orderProduct = OrderProduct.createOrderProduct(member, product.getId(), orderProductCreate.getOrderPrice(), orderProductCreate.getAmount(),
                    order, product.getName(), orderProductCreate.getOrderPrice()/orderProductCreate.getAmount());

            orderProductRepository.save(orderProduct);

            Cart cart = cartRepository.findByProductIdAndMember(product.getId(), member).orElseThrow(
                    () -> new BusinessException(CART_NO_PRODUCTS)
            );

            cartRepository.deleteById(cart.getId());

        }

        orderRepository.save(order);


    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderPageResponse> orderFindMember(Pageable pageable) {
        Member member = getMember();
        Page<Order> orderList = orderRepository.findAll(pageable);

        return orderList.stream().filter(findOrder -> findOrder.getMemberId().equals(member.getId()))
                .map(order -> OrderPageResponse.toResponse(order, orderList)).toList();
    }


    @Override
    public OrderResponse orderFindOne(Long orderId) {
        Member member = getMember();
        Order order = orderRepository.findByIdAndMemberId(orderId, member.getId()).orElseThrow(
                () -> new BusinessException(NOT_FOUND_ORDERS));

        OrderResponse orderResponse = OrderResponse.toResponse(order);
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder(order);
        List<Long> list = new ArrayList<>();

        for (OrderProduct orderProduct : orderProducts) {
            list.add(orderProduct.getProductId());
        }
        orderResponse.setProductId(list);
        return orderResponse;
    }


    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        return memberRepository.findByLoginId(loginId).orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
    }
}
