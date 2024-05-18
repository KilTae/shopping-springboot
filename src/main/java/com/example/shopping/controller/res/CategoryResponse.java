package com.example.shopping.controller.res;

import com.example.shopping.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long categoryId;
    private String category;

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .category(category.getCategory()).build();
    }
}
