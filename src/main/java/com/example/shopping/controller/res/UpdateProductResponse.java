package com.example.shopping.controller.res;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductResponse {
    private Long productId;
    private int productPrice;
    private boolean changeCheck;
}
