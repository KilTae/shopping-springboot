package com.example.shopping.controller;

import com.example.shopping.controller.req.LoginRequest;
import com.example.shopping.controller.req.MemberEditRequest;
import com.example.shopping.controller.req.MemberSignupRequest;
import com.example.shopping.controller.res.MemberResponse;
import com.example.shopping.global.config.security.JwtTokenDto;
import com.example.shopping.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/member/exist")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //
    public void memberLoginIdDuplicateCheck(String loginId) {
        memberService.logInDuplicateCheck(loginId);
    }

    @PostMapping("/member/login")
    @ResponseStatus(HttpStatus.OK)
    //
    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        return memberService.login(loginRequest);
    }

    @GetMapping("/member/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SELLER')")
    @ResponseStatus(HttpStatus.OK)
    //
    public MemberResponse findByDetailMyInfo() {
        return memberService.findByDetailMyInfo();
    }

    @PutMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SELLER')")
    //
    public void memberEdit(@RequestBody @Valid MemberEditRequest memberEditRequest) {
        memberService.memberEdit(memberEditRequest);
    }

    @DeleteMapping("/members")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('User', 'SELLER', 'ADMIN')")
    //.
    public void memberDelete() {
        memberService.memberDelete();
    }
}