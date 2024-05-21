package com.example.shopping.controller.res;


import com.example.shopping.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long memberId;
    private Long productId;
    private int totalAmount;
    private int totalPrice;
    private Long optionNumber;


    public CartResponse(Cart cart) {
        this.memberId = cart.getId(); // ref
        this.productId = cart.getProductId();
        this.totalAmount = cart.getTotalAmount();
        this.totalPrice = cart.getTotalPrice();
        this.optionNumber = cart.getOptionNumber();
    }
}
