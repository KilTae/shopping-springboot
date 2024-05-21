package com.example.shopping.controller;

import com.example.shopping.controller.req.CartCreateRequest;
import com.example.shopping.controller.req.CartEditRequest;
import com.example.shopping.controller.res.CartResponse;
import com.example.shopping.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    public void cartAddGoods( @RequestBody @Valid CartCreateRequest cartCreateRequest) {
        cartService.cartAddProduct(cartCreateRequest);
    }

    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
    public Page<CartResponse> cartFind(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return cartService.cartFindMember(pageable);
    }

    @PutMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")

    public void cartEdit(@PathVariable("cartId") Long cartId, @RequestBody CartEditRequest cartEditRequest) {
        cartService.editCartItem(cartId, cartEditRequest);
    }

    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER')")
   // @ApiOperation(value = "장바구니 상품 삭제")
    public void cartDeleteGoods(@PathVariable("cartId") Long cartId) {
        cartService.cartDeleteProduct(cartId);
    }
}
