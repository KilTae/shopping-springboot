package com.example.shopping.controller.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRequest {
    @NotNull(message = "파일이름을 입력하세요.")
    private String fileName;

    @NotNull(message = "파일경로를 입력하세요.")
    private String fileUrl;

    @NotNull(message = "상품번호를 입력하세요.")
    private Long goodsId;
}
