package com.example.shopping.controller.res;

import com.example.shopping.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long memberId;
    private Long goodsId;
    private String comment;
    private List<ReplyResponse> replyResponse;

    public ReviewResponse(Review review) {
        this.memberId = review.getMemberId();
        this.goodsId = review.getProduct().getId();
        this.comment = review.getComment();
        this.replyResponse = review.getReplies()
                .stream()
                .map(ReplyResponse::new).collect(Collectors.toList());
    }
}
