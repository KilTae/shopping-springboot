package com.example.shopping.controller;

import com.example.shopping.controller.req.ReplyCreateRequest;
import com.example.shopping.controller.req.ReplyEditRequest;
import com.example.shopping.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {
    private final ReplyService replyService;

    // 대댓글 생성
    @PostMapping("/replies")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SELLER')")
    //@ApiOperation(value = "대댓글 등록")
    public void replyCreate(Long reviewId, @RequestBody @Valid ReplyCreateRequest request) {
        replyService.replyCreate(reviewId, request);
    }

    // 대댓글 수정
    @PutMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('SELLER')")
  //  @ApiOperation(value = "대댓글 수정")
    public void replyEdit(@PathVariable("replyId") Long replyId, @RequestBody @Valid ReplyEditRequest request) {
        replyService.replyEdit(replyId, request);
    }

    // 대댓글 삭제
    @DeleteMapping("/replies/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
   // @ApiOperation(value = "대댓글 삭제")
    public void replyDelete(@PathVariable("replyId") Long replyId) {
        replyService.replyDelete(replyId);
    }
}
