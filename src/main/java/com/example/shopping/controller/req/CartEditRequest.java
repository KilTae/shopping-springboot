package com.example.shopping.controller.req;

import jakarta.validation.constraints.NotNull;
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
public class CartEditRequest {

    @NotNull(message = "구매 수량을 입력해주세요.")
    @Positive(message = "구매 수량은 1개 이상만 가능합니다.")
    private int amount; // 구매 수량

    @Nullable
    private Long optionNumber; // 옵션번호
}
