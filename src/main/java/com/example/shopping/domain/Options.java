package com.example.shopping.domain;


import com.example.shopping.controller.req.OptionCreateRequest;
import com.example.shopping.domain.convert.OptionConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

public class Options extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Convert(converter = OptionConverter.class)
    private List<OptionCreate> optionValue;

    @Column(nullable = false)
    private int totalPrice;

    @Column
    private String description;

    @Builder
    public Options(Long id, Product product, List<OptionCreate> optionValue, int totalPrice, String description) {
        this.id = id;
        this.product = product;
        this.optionValue = optionValue;
        this.totalPrice = totalPrice;
        this.description = description;
    }

    //생성자
    public static Options toOption(OptionCreateRequest optionCreateRequest, Product product) {
        return Options.builder()
                .product(product)
                .optionValue(optionCreateRequest.getOptionValue())
                .totalPrice(optionCreateRequest.getTotalPrice())
                .description(optionCreateRequest.getOptionDescription())
                .build();
    }


    //연관관계 양방향 매핑

    public void setProduct(Product product) {
        this.product = product;
        product.getOptions().add(this);
    }
}
