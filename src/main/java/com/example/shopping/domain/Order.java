package com.example.shopping.domain;


import com.example.shopping.controller.req.OrderCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table (name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String detailAddress;

    @Column
    private String requirement;

    @Column(nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private String impUid;         // 아임포트 발급 ID ex)imp_727855699150

    @Column(nullable = false)
    private String merchantId;

    @Builder
    public Order(Long memberId, String name, String phone, String zipcode, String detailAddress, String requirement, int totalPrice, String impUid, String merchantId) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.zipcode = zipcode;
        this.detailAddress = detailAddress;
        this.requirement = requirement;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.COMPLETE;
        this.impUid = impUid;
        this.merchantId = merchantId;
    }

    public static Order toOrder(OrderCreateRequest orderCreateRequest, Member member) {

        int totalPrice = 0;
        for (OrderCreateRequest.orderProductCreate orderItemCreate : orderCreateRequest.getOrderProductCreates()) {
            totalPrice += orderItemCreate.getOrderPrice();
        }

        return Order.builder()
                .memberId(member.getId())
                .name(orderCreateRequest.getName())
                .phone(orderCreateRequest.getPhone())
                .zipcode(orderCreateRequest.getZipcode())
                .detailAddress(orderCreateRequest.getDetailAddress())
                .requirement(orderCreateRequest.getRequirement())
                .totalPrice(totalPrice)
                .impUid(orderCreateRequest.getImpUid())
                .merchantId(orderCreateRequest.getMerchantId())
                .build();
    }

    // 주문 상태 변경
    public void orderStatusChangeCancel() {
        this.orderStatus = OrderStatus.CANCEL;
    }


}
