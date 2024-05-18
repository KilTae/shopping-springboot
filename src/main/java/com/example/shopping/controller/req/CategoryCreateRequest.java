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
public class CategoryCreateRequest {
    @NotBlank(message = "카테고리 값은 필수입니다.")
    private String category;
}
