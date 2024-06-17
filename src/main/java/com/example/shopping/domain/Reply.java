package com.example.shopping.domain;


import com.example.shopping.controller.req.ReplyCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reply")
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String comment;

    @Builder
    public Reply(Long memberId, Review review, String comment) {
        this.memberId = memberId;
        this.review = review;
        this.comment = comment;
    }

    public void edit(String comment) {
        this.comment = comment;
    }

    // 대댓글 생성
    public static Reply createReply(Member member, Review review, ReplyCreateRequest replyCreateRequest) {
        return Reply.builder()
                .memberId(member.getId())
                .review(review)
                .comment(replyCreateRequest.getReplyComment())
                .build();

    }
}