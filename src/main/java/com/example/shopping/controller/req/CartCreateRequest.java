package com.example.shopping.controller.req;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartCreateRequest {
    @NotNull(message = "상품의 번호를 입력해 주세요")
    private Long productId;

    @NotNull(message = "구매 수량을 입력해주세요.")
    @Positive(message = "구매 수량은 1개 이상만 가능합니다.")
    private int amount;

    @Nullable
    private Long optionNumber;
}
