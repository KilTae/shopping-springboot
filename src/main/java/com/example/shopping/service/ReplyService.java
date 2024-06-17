package com.example.shopping.service;

import com.example.shopping.controller.req.ReplyCreateRequest;
import com.example.shopping.controller.req.ReplyEditRequest;

public interface ReplyService {
    // 리뷰 대댓글 생성
    void replyCreate(Long reviewId, ReplyCreateRequest replyCreateRequest);

    // 리뷰 대댓글 수정
    void replyEdit(Long replyId, ReplyEditRequest ReplyEditRequest);

    // 리뷰 대댓글 삭제
    void replyDelete(Long replyId);
}
