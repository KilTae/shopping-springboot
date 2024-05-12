package com.example.shopping.domain;

import com.example.shopping.controller.req.MemberSignupRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email; //왜 javax -> jakarta ?
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder //
@NoArgsConstructor (access = AccessLevel.PROTECTED)//
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "member_id") //회원번호 PK
    private Long id;

    @Column (nullable = false, length = 20, unique = true)
    private String loginId; // 로그인 아이디

    @Column(unique = false, length = 50)
    @Email
    private String email; //이메일

    @Column(unique = false)
    private String password; //패스워드

    @Column(nullable = false)
    private String zipcode;         //우편번호

    @Column(nullable = false)
    private String detailAddress;   //상세주소


    @Column (nullable = false, length = 20)
    private String name; //이름

    @Column (nullable = false, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; //소셜 로그인 유무


    @OneToMany (mappedBy = "member")
    @Builder.Default
    private List<Role> roles = new ArrayList<>();


    // 회원 생성 (기존)
    public static Member create(MemberSignupRequest memberSignupRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .loginId(memberSignupRequest.getLoginId())
                .password(memberSignupRequest.getPassword())
                .name(memberSignupRequest.getPassword())
                .email(memberSignupRequest.getEmail())
                .zipcode(memberSignupRequest.getZipcode())
                .detailAddress(memberSignupRequest.getDetailAddress())
                .phone(memberSignupRequest.getPhone())
                .loginType(LoginType.NO_SOCIAL)
                .build();
    }


    //카카오 로그인 회원 생성
    /*
    // 카카오 로그인 -> 회원 생성
    public static Member kakaoCreate(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .loginId(loginRequest.getLoginId())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .name("")
                .zipcode("")
                .detailAddress("")
                .email(loginRequest.getEmail())
                .phone("")
                .loginType(LoginType.KAKAO)
                .build();
    }
    */


    //회원 수정


    //회원 권한 설정

    public void setRoles(Role role) {
        this.getRoles().add(role);
    }




}
