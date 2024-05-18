package com.example.shopping.controller.req;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEditRequest {
    @NotNull(message = "비밀번호를 입력하세요.")
    private String password;

    @NotNull(message = "우편번호를 입력하세요.")
    private String zipcode;

    @NotNull(message = "상세주소를 입력하세요.")
    private String detailAddress;

    @NotNull(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 맞지않습니다.")
    private String email;

    @NotNull(message = "전화번호를 입력하세요.")
    private String phone;
}
