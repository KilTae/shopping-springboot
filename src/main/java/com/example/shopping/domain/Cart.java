package com.example.shopping.domain;


import com.example.shopping.controller.req.CartEditRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart")
@Entity
public class Cart extends BaseTimeEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Long productId; // ref

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private int totalPrice;

    @Column
    private Long optionNumber; // ref

    @Builder
    public Cart(Long id, Member member, Long productId, int totalAmount, int totalPrice, Long optionNumber) {
        this.id = id;
        this.member = member;
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        this.optionNumber = optionNumber;
    }

    public void editCartIncludeOption(Options options, CartEditRequest cartEditRequest) {
        int goodsTotalPrice = options.getTotalPrice();
        this.totalAmount = cartEditRequest.getAmount();
        this.optionNumber = cartEditRequest.getOptionNumber();
        this.totalPrice = goodsTotalPrice * (cartEditRequest.getAmount());

    }

    public void editCartExcludeOption(Product product , CartEditRequest cartEditRequest) {
        this.totalAmount = cartEditRequest.getAmount();
        this.optionNumber = cartEditRequest.getOptionNumber();
        this.totalPrice = product.getPrice() * (cartEditRequest.getAmount());

    }




}
