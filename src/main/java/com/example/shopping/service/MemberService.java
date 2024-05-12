package com.example.shopping.service;

import com.example.shopping.controller.req.MemberSignupRequest;

public interface MemberService {

    void memberSignup(MemberSignupRequest memberSignupRequest);

    void logInDuplicateCheck(String loginId);
}
