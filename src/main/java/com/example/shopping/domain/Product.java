package com.example.shopping.domain;


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
    private int like;

   // private int buy;

    @Column
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> imags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    // @Builder 생성


    // 업데이트

}
