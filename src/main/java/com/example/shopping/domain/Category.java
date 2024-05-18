package com.example.shopping.domain;


import com.example.shopping.controller.req.CategoryCreateRequest;
import com.example.shopping.controller.res.CategoryResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String category;


    @Builder
    public Category(String category) {
        this.category = category;
    }

    //builder
    public static Category toCategory(CategoryCreateRequest categoryCreateRequest) {
        return Category.builder()
                .category(categoryCreateRequest.getCategory())
                .build();
    }

    //카테고리 수정

}
