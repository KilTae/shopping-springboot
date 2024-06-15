package com.example.shopping.controller;

import com.example.shopping.controller.req.ReviewCreateRequest;
import com.example.shopping.controller.req.ReviewEditRequest;
import com.example.shopping.controller.res.ReviewResponse;
import com.example.shopping.service.ReviewService;
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
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER')")
    //@ApiOperation(value = "리뷰 생성")
    public void reviewCreate(@Valid @RequestHeader("orderProductId") Long orderProductId, @RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
        reviewService.reviewCreate(orderProductId, reviewCreateRequest);
    }

    @GetMapping("/product/reviews")
    @ResponseStatus(HttpStatus.OK)
 //   @ApiOperation(value = "리뷰 조회")
    public Page<ReviewResponse> reviewFindAll(Long goodsId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return reviewService.reviewFindAll(goodsId, pageable);
    }

    // 리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER')")
  //  @ApiOperation(value = "리뷰 수정")
    public void reviewEdit(@PathVariable(name = "reviewId") Long reviewId, @RequestBody @Valid ReviewEditRequest reviewEditRequest) {
        reviewService.reviewEdit(reviewId, reviewEditRequest);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
 //   @ApiOperation(value = "리뷰 삭제")
    public void reviewDelete(@PathVariable(name = "reviewId") Long reviewId) {
        reviewService.reviewDelete(reviewId);
    }
}
