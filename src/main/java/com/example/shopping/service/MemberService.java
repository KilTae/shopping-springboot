package com.example.shopping.service;

import com.example.shopping.controller.req.LoginRequest;
import com.example.shopping.controller.req.MemberEditRequest;
import com.example.shopping.controller.req.MemberSignupRequest;
import com.example.shopping.controller.res.MemberResponse;
import com.example.shopping.global.config.security.JwtTokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;


public interface MemberService {

    void memberSignup(MemberSignupRequest memberSignupRequest);

    void logInDuplicateCheck(String loginId);

    JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException;

    MemberResponse findByDetailMyInfo();

    void memberEdit(MemberEditRequest memberEditRequest);

    void memberDelete();


}
