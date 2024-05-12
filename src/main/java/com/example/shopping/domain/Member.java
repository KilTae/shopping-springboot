package com.example.shopping.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email; //왜 javax -> jakarta ?
import lombok.*;

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

    private String password; //패스워드

    private String address; //주소 // 따로 주소지를 빼는게 좋을 것 같음.

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


    //카카오 로그인 회원 생성


    //회원 수정


    //회원 권한 설정





}
