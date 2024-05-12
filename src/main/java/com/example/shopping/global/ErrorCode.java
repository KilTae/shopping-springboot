package com.example.shopping.global;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "테스트 에러"),

    // 회원

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_EQUAL_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호를 확인해주세요"),
    CHECK_LOGIN_ID_OR_PASSWORD(HttpStatus.NOT_FOUND, "아이디 또는 비밀번호를 확인해주세요."),
    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디 입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),
    OTHER_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "로그인 타입을 확인해주세요");


    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;

}
