package com.example.shopping.controller.req;

//import com.example.shopping.domain.QOrderProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;


import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateRequest {
    @NotBlank(message = "수취인 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "수취인 전화번호를 입력하세요.")
    private String phone;

    @NotBlank(message = "수취인 우편번호를 입력하세요.")
    private String zipcode;

    @NotBlank(message = "수취인 상세주소를 입력하세요.")
    private String detailAddress;

    @Nullable
    private String requirement;

    @NotNull(message = "주문상품을 입력하세요.")
    private List<orderProductCreate> orderProductCreates;

    @NotNull(message = "총 주문 금액을 입력하세요.")
    private int totalPrice;

    @NotBlank(message = "아임포트 결제 ID 를 입력하세요.")
    private String impUid;

    @NotBlank(message = "주문번호 를 입력하세요.")
    private String merchantId;

    @NotNull(message = "카드사를 입력하세요.")
    private String cardCompany;

    @NotNull(message = "카드일련번호를 입력하세요.")
    private String cardNumber;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class orderProductCreate {
        private Long productId;   // 상품 ID
        private int amount;    // 상품 수량
        private int orderPrice;     // 각 상품 주문 가격
    }
}
