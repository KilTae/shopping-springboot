package com.example.shopping.controller.res;
import com.example.shopping.domain.Image;
import com.example.shopping.domain.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    private String fileUrl;

    public static List<ImageResponse> toResponse(Product product) {
        List<ImageResponse> list = new ArrayList<>();
        for (Image image : product.getImags()) {
            ImageResponse imageResponse = ImageResponse.builder().fileUrl(image.getFileUrl()).build();
            list.add(imageResponse);
        }
        return list;
    }
}
