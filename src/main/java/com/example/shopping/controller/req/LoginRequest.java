package com.example.shopping.controller.req;

import com.example.shopping.domain.LoginType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "닉네임은 필수값입니다.")
    private String loginId;

    @NotNull(message = "비밀번호는 필수값입니다.")
    private String password;

    @NotNull(message = "로그인타입은 필수값입니다.")
    private LoginType loginType; // NO_SOCIAL , KAKAO

    @Nullable
    private String email;       // 선택동의 항목 (email)
}
