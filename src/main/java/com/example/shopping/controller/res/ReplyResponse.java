package com.example.shopping.controller.res;

import com.example.shopping.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponse {
    private String comment;

    public ReplyResponse(Reply reply) {
        this.comment = reply.getComment();
    }
}
