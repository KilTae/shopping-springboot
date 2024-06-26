package com.example.shopping.controller.res;
import java.util.List;

import lombok.*;
import com.example.shopping.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private String loginId;         //회원 ID
    private String name;            //이름
    private String zipcode;         //우편번호
    private String detailAddress;   //상세주소
    private String email;           //이메일
    private String phone;           //핸드폰번호
    private List<String> roles;

    // Member -> MemberResponse 변환
    public static MemberResponse toResponse(Member m) {
        return MemberResponse.builder()
                .loginId(m.getLoginId())
                .name(m.getName())
                .zipcode(m.getZipcode())
                .detailAddress(m.getDetailAddress())
                .email(m.getEmail())
                .phone(m.getPhone())
                .build();
    }
}
