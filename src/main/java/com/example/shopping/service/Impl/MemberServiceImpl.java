package com.example.shopping.service.Impl;


import com.example.shopping.controller.req.MemberSignupRequest;
import com.example.shopping.domain.Role;
import com.example.shopping.domain.RoleType;
import com.example.shopping.global.ErrorCode;
import com.example.shopping.global.exception.BusinessException;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.RoleRepository;
import com.example.shopping.service.MemberService;
import com.example.shopping.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
   /* @Value("${cloud.aws.s3.bucket}")
    private String bucket;*/

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void memberSignup(MemberSignupRequest membersignuprequest) {
        DuplicatedLoginIdCheck(memberRepository.findByLoginId(membersignuprequest.getLoginId()).isPresent()); //
        Member member = Member.create(membersignuprequest, passwordEncoder);

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

    private void DuplicatedLoginIdCheck(boolean duplicatedCheck) {
        if(duplicatedCheck) throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);



    }

}
