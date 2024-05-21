package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.CartCreateRequest;
import com.example.shopping.controller.req.CartEditRequest;
import com.example.shopping.controller.res.CartResponse;
import com.example.shopping.domain.Cart;
import com.example.shopping.domain.Member;
import com.example.shopping.domain.Options;
import com.example.shopping.domain.Product;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.CartRepository;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.OptionRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImel implements CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Override
    public void cartAddProduct(CartCreateRequest cartCreateRequest) {
        Member member = getMember();
        Product product = ExistProductCheck(cartCreateRequest.getProductId());

        Options options = null;

        if(!optionRepository.findByProductId(cartCreateRequest.getProductId()).isEmpty()) {
            options = optionRepository.findByIdAndProductId(cartCreateRequest.getOptionNumber(), product.getId()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_OPTION)
            );
        }

        if(cartRepository.findByProductIdAndMember(product.getId(), member).isPresent())
            throw new BusinessException(ErrorCode.CART_IN_GOODS_DUPLICATED);


        int productTotalPrice = product.getPrice();

        if(!product.getOptions().isEmpty()) {
            productTotalPrice = options.getTotalPrice();
        }

        Cart cart = Cart.builder()
                .member(member)
                .productId(product.getId())
                .totalAmount(cartCreateRequest.getAmount())
                .totalPrice(productTotalPrice * cartCreateRequest.getAmount())
                .optionNumber(cartCreateRequest.getOptionNumber())
                .build();


        cartRepository.save(cart);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartResponse> cartFindMember(Pageable pageable) {
        Member member = getMember();

        Page<Cart> carts = cartRepository.findAllByMemberId(member.getId(), pageable);
        return carts.map(CartResponse::new);
    }


    @Override
    public void editCartItem(Long cartId, CartEditRequest cartEditRequest) {
        Member member = getMember();
        Cart cart = ExistMemberCartCheck(cartId, member);
        Product product = ExistProductCheck(cart.getProductId());

        List<Options> options = optionRepository.findByProductId(product.getId());

        if(options.isEmpty()) {
            System.out.println("잘나옴 노옵션");
            if(cartEditRequest.getOptionNumber() != null) throw new BusinessException(ErrorCode.NOT_FOUND_OPTION);
            cart.editCartExcludeOption(product, cartEditRequest);
            System.out.println(cartEditRequest.getAmount());
            return;
        }

        for(Options option: options) {
            System.out.println("잘나옴 옵션");
            if(!option.getId().equals(cartEditRequest.getOptionNumber())) continue;
            cart.editCartIncludeOption(option, cartEditRequest);
            System.out.println(cartEditRequest.getAmount());

            return;
        }
    }

    @Override
    public void cartDeleteProduct(Long cartId) {
        Member member = getMember();
        Cart cart = ExistMemberCartCheck(cartId, member);
        cartRepository.deleteById(cart.getId());
    }

    private Cart ExistMemberCartCheck(Long cartId, Member member) {
        return cartRepository.findByIdAndMember(cartId, member).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CART));
    }

    private Product ExistProductCheck(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_GOODS));
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

    }

}
