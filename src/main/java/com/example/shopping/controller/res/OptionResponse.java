package com.example.shopping.controller.res;

import com.example.shopping.domain.OptionCreate;
import com.example.shopping.domain.Options;
import com.example.shopping.domain.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionResponse {

    private List<OptionCreate> optionValue;
    private int totalPrice;
    private String optionDescription;

    public static List<OptionResponse> toResponse(Product product) {
        List<OptionResponse> list = new ArrayList<>();
        for (Options options : product.getOptions()) {
            OptionResponse optionResponse = OptionResponse.builder()
                    .optionValue(options.getOptionValue())
                    .totalPrice(options.getTotalPrice())
                    .optionDescription(options.getDescription())
                    .build();
            list.add(optionResponse);
        }

        return list;
    }
}
