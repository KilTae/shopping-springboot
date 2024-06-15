package com.example.shopping.service;

import com.example.shopping.controller.req.ReviewCreateRequest;
import com.example.shopping.controller.req.ReviewEditRequest;
import com.example.shopping.controller.res.ReviewResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    // 리뷰생성
    void reviewCreate(Long orderItemId, ReviewCreateRequest reviewCreateRequest);

    // 리뷰 전체
    Page<ReviewResponse> reviewFindAll(Long goodsId, Pageable pageable);

    // 리뷰 수정
    void reviewEdit(Long reviewId, ReviewEditRequest reviewEditRequest);

    // 리뷰 삭제
    void reviewDelete(Long reviewId);
}
