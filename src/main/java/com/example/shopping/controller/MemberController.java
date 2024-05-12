package com.example.shopping.controller;

import com.example.shopping.controller.req.MemberSignupRequest;
import com.example.shopping.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    @ResponseStatus(HttpStatus.CREATED)
   // @ApiOperation(value = "회원 가입")
    public void memberSignup(@RequestBody @Valid MemberSignupRequest request) {
        memberService.memberSignup(request);
    }
}
