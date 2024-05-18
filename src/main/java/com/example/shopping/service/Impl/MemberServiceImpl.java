package com.example.shopping.service.Impl;


import com.example.shopping.controller.req.LoginRequest;
import com.example.shopping.controller.req.MemberEditRequest;
import com.example.shopping.controller.req.MemberSignupRequest;
import com.example.shopping.controller.res.MemberResponse;
import com.example.shopping.domain.*;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.config.security.JwtTokenDto;
import com.example.shopping.global.config.security.TokenProvider;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.CartRepository;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.RoleRepository;
import com.example.shopping.service.MemberService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    //@Value("${cloud.aws.s3.bucket}")
    //private String bucket;

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public void memberSignup(MemberSignupRequest membersignuprequest) {
        DuplicatedLoginIdCheck(memberRepository.findByLoginId(membersignuprequest.getLoginId()).isPresent()); //
        Member member = Member.create(membersignuprequest, passwordEncoder);
        memberRepository.save(member);

        for(RoleType role : membersignuprequest.getRoles()) {
            Role saveRole = Role.builder()
                    .roleType(role)
                    .member(member)
                    .build();
            roleRepository.save(saveRole);
        }

    }

    @Override
    public void logInDuplicateCheck(String loginId) {
        DuplicatedLoginIdCheck(memberRepository.findByLoginId(loginId).isPresent());
    }

    @Override
    public JwtTokenDto login(LoginRequest loginRequest) throws JsonProcessingException {
        if(loginRequest.getLoginType().equals(LoginType.KAKAO)) {
            Member member = Member.kakaoCreate(loginRequest, passwordEncoder);

            if(memberRepository.findByEmailAndLoginId(loginRequest.getEmail(), loginRequest.getLoginId()).isPresent()) {
                return tokenProvider.generateToken(loginRequest);
            }

            DuplicatedLoginIdCheck(memberRepository.findByLoginId(loginRequest.getLoginId()).isPresent());

            Role role = Role.builder()
                    .member(member)
                    .roleType(RoleType.ROLE_USER)
                    .build();
            roleRepository.save(role);

            member.setRoles(role);
            memberRepository.save(member);


            return tokenProvider.generateToken(loginRequest);
        }

        Member member = memberRepository.findByLoginId(loginRequest.getLoginId()).orElseThrow(()
                -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        if(!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.NOT_EQUAL_PASSWORD);
        }


        return tokenProvider.generateToken(loginRequest);
    }

    private void DuplicatedLoginIdCheck(boolean duplicatedCheck) {
        if(duplicatedCheck) throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);

    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse findByDetailMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("인증:");
        System.out.println(authentication.getName());
        Member member = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        MemberResponse memberResponse = MemberResponse.toResponse(member);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        memberResponse.setRoles(list);

        return memberResponse;
    }

    @Override
    public void memberEdit(MemberEditRequest memberEditRequest) {
        Member member = getMember();
        member.edit(memberEditRequest, passwordEncoder);
    }

    @Override
    public void memberDelete() {
        Member member = getMember();
        member.setDeletedAt();

        for(Role role : member.getRoles()) {
            if(role.getRoleType().equals(RoleType.ROLE_USER)) {
                List<Cart> cartList = cartRepository.findByMemberId(member.getId());
                cartRepository.deleteAll(cartList);
            }

           // memberRepository.deleteAllById(member);
        //    if(role.getRoleType().equals(RoleType.ROLE_SELLER)) {
                //    for(Product prodcut : cartList){ 이미지, cart repository, service 계층 생성시 만들기
                //      List<Image> imageList = imageRepository
                //}
        //    }

        }
    }


    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        return memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER)
        );


    }

}
