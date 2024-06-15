package com.example.shopping.domain;


import com.example.shopping.controller.req.ReviewCreateRequest;
import com.example.shopping.controller.req.ReviewEditRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long Id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = true)
    private String comment;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    // image entity를 만들어아 하나?

    //생성자

    //양방향매핑

    //리뷰 수정

    @Builder
    public Review(Long memberId, Product product, String comment) {
        this.memberId = memberId;
        this.product = product;
        this.comment = comment;
    }

    // 연관관계 편의 메서드 (양방향 매핑)
    public void setGoods(Product product) {
        this.product = product;
        product.getReviews().add(this);
    }

    // 리뷰 생성
    public static Review createReview(Member member, Product product, ReviewCreateRequest reviewCreateRequest) {
        return Review.builder()
                .memberId(member.getId())
                .product(product)
                .comment(reviewCreateRequest.getContent())
                .build();
    }

    // 리뷰 수정
    public void edit(ReviewEditRequest reviewEditRequest) {
        this.comment = reviewEditRequest.getContent();
    }



}
