package com.example.shopping.controller.req;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    @NotNull(message = "상품번호를 입력하세요")
    private Long productId;

    @NotNull(message = "가격을 입력하세요")
    private int productPrice;

    @Nullable
    private Long optionId;

}
