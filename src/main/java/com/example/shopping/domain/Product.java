package com.example.shopping.domain;


import com.example.shopping.controller.req.ProductCreateRequest;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private int price;

  //  private int discount

    @Column
    private int view;

    @Column
    private int likeNumber;

   // private int buy;

    @Column
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> imags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Options> options = new ArrayList<>();

    @Builder
    public Product(Long id, Long memberId, String name, Category category, int price, String description) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    // @Builder 생성
    public static Product create(ProductCreateRequest productCreateRequest, Category category, Member member) {
        return Product.builder()
                .name(productCreateRequest.getName())
                .memberId(member.getId())
                .category(category)
                .price(productCreateRequest.getPrice())
                .description(productCreateRequest.getGoodsDescription())
                .build();

    }



    // 업데이트

}
