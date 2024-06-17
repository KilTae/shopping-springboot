package com.example.shopping.controller.req;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyCreateRequest {
    @NotNull(message = "댓글을 입력하세요.")
    @Size(max = 255, message = "최대 255 글자까지만 작성할 수 있습니다.")
    private String replyComment;
}
