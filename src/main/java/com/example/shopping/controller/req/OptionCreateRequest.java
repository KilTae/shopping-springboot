package com.example.shopping.controller.req;

import com.example.shopping.domain.OptionCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionCreateRequest {
    @NotNull(message = "옵션을 선택하세요.")
    private List<OptionCreate> optionValue;

    @NotNull(message = "총 가격을 입력하세요.")
    private int totalPrice;

    @Nullable
    private String optionDescription;

}
