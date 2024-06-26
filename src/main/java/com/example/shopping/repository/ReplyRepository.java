package com.example.shopping.repository;

import com.example.shopping.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.review.Id = :reviewId")
    List<Reply> findByReviewId(@Param("reviewId") Long reviewId);

    Optional<Reply> findByIdAndMemberId(Long replyId, Long memberId);
}
