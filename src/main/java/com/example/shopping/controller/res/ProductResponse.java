package com.example.shopping.controller.res;


import com.example.shopping.domain.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long memberId;
    private Long productId;
    private String productName;
    private String categoryName;
    private int price;
    private String description;
    private List<ImageResponse> imageList;
    private List<OptionResponse> options;

    @QueryProjection
    public ProductResponse(Product product) {
        this.memberId = product.getMemberId();
        this.productId = product.getId();
        this.productName = product.getName();
        this.categoryName = product.getCategory().getCategory();
        this.price = product.getPrice();
     //   this.imageList = ImageResponse.toResponse(goods);
        this.options = OptionResponse.toResponse(product);

    }

}
