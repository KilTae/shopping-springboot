package com.example.shopping.controller.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEditRequest {

    @NotBlank(message = "카테고리는 필수 값 입니다.")
    private String category;
}
