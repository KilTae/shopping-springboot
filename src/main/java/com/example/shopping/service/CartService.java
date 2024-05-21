package com.example.shopping.service;

import com.example.shopping.controller.req.CartCreateRequest;
import com.example.shopping.controller.req.CartEditRequest;
import com.example.shopping.controller.res.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    void cartAddProduct(CartCreateRequest cartCreateRequest);

    Page<CartResponse> cartFindMember(Pageable pageable);

    void editCartItem(Long cartId, CartEditRequest cartEditRequest);

    void cartDeleteProduct(Long cartId);
}
