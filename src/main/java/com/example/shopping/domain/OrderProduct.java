package com.example.shopping.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_product")
@Entity
public class OrderProduct extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;        // 회원 ID

    @Column(nullable = false)
    private Long productId;         //상품(다대일)

    @Column(nullable = false)
    private String productName;     // 상품 이름

    @Column(nullable = false)
    private int price;            // 상품 가격

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;          //주문(다대일)

    @Column(nullable = false)
    private int amount;           //주문수량개수

    @Column(nullable = false)
    private int orderPrice;      //주문수량가격

    //빌더 생성
    //주문_상품 생성

}
