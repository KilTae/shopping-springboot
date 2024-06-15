package com.example.shopping.controller.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchCondition {
    private Integer priceMin;
    private Integer priceMax;
    private String category;

    public ProductSearchCondition(Integer priceMin, Integer priceMax, String category) {
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.category = category;
    }
}
