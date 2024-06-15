package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.ReviewCreateRequest;
import com.example.shopping.controller.req.ReviewEditRequest;
import com.example.shopping.controller.res.ReviewResponse;
import com.example.shopping.domain.OrderProduct;
import com.example.shopping.domain.Product;
import com.example.shopping.domain.Review;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.OrderProductRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ReviewRepository;
import com.example.shopping.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.shopping.domain.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 리뷰생성 - 결제한 사람만 리뷰 작성이 가능
    @Override
    public void reviewCreate(Long orderProductId, ReviewCreateRequest reviewCreateRequest) {
        Member member = getMember();

        System.out.println("product id:");
        System.out.println(orderProductId);

        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElseThrow(
                () -> new BusinessException(ErrorCode.NO_BUY_ORDER));



        // 주문한 상품의 회원과 로그인한 회원이 다르면 예외처리
        if (!orderProduct.getMemberId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.NOT_BUY_GOODS);
        }

        Product products = productRepository.findById(orderProduct.getProductId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_BUY_GOODS));

        Review review = Review.createReview(member, products, reviewCreateRequest);
        review.setGoods(products);
        reviewRepository.save(review);
    }

    // 리뷰 전체
    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> reviewFindAll(Long goodsId, Pageable pageable) {
        Product product = productRepository.findById(goodsId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_BUY_GOODS));
        Page<Review> reviews = reviewRepository.findAllByProduct(product, pageable);
        return reviews.map(ReviewResponse::new);
    }

    // 리뷰 수정
    @Override
    public void reviewEdit(Long reviewId, ReviewEditRequest reviewEditRequest) {
        Member member = getMember();
        Review review = ExistReviewCheck(reviewId);
        WriteReviewMemberEqualLoginMemberCheck(member, review);
        review.edit(reviewEditRequest);
    }

    // 리뷰 삭제
    @Override
    public void reviewDelete(Long reviewId) {
        Member member = getMember();
        Review review = ExistReviewCheck(reviewId);
        WriteReviewMemberEqualLoginMemberCheck(member, review);
        reviewRepository.delete(review);
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        return memberRepository.findByLoginId(loginId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    private Review ExistReviewCheck(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));
    }

    private void WriteReviewMemberEqualLoginMemberCheck(Member member, Review review) {
        if (!review.getMemberId().equals(member.getId()))
            throw new BusinessException(ErrorCode.NOT_MATCH_REVIEW);
    }

}
