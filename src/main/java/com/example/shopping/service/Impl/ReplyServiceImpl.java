package com.example.shopping.service.Impl;

import com.example.shopping.controller.req.ReplyCreateRequest;
import com.example.shopping.controller.req.ReplyEditRequest;
import com.example.shopping.domain.Review;
import com.example.shopping.domain.Member;
import com.example.shopping.domain.Reply;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.ReplyRepository;
import com.example.shopping.repository.ReviewRepository;
import com.example.shopping.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    // 대댓글 생성
    @Override
    public void replyCreate(Long reviewId, ReplyCreateRequest replyCreateRequest) {
        Member member = getMember();

        // 대댓글 추가할 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));

        // 해당 상품을 판매하고 있는 회원인지 확인
        if (!review.getProduct().getMemberId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.NOT_SELLING_GOODS);
        }

        Reply reply = Reply.createReply(member, review, replyCreateRequest);
        replyRepository.save(reply);
    }
    // 대댓글 수정
    @Override
    public void replyEdit(Long replyId, ReplyEditRequest ReplyEditRequest) {
        Member member = getMember();
        Reply reply = ExistMemberWriteReplyCheck(replyId, member);
        reply.edit(ReplyEditRequest.getComment());
    }

    // 대댓글 삭제
    @Override
    public void replyDelete(Long replyId) {
        Member member = getMember();
        Reply reply = ExistMemberWriteReplyCheck(replyId, member);
        replyRepository.delete(reply);
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    //회원이 작성한 대댓글이 존재여부 확인
    private Reply ExistMemberWriteReplyCheck(Long replyId, Member member) {
        return replyRepository.findByIdAndMemberId(replyId, member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REPLY));
    }
}
