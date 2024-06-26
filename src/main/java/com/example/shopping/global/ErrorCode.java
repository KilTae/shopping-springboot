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
    OTHER_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "로그인 타입을 확인해주세요"),
    // 권한
    NOT_FOUND_AUTHORITY(HttpStatus.NOT_FOUND, "존재하지 않는 권한입니다."),

    // 상품
    NOT_FOUND_GOODS(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    DUPLICATE_GOODS(HttpStatus.BAD_REQUEST, "이미 존재하는 상품입니다."),
    NOT_BUY_GOODS(HttpStatus.BAD_REQUEST, "구매한 상품이 아닙니다."),
    NOT_SELLING_GOODS(HttpStatus.BAD_REQUEST, "판매하고 있는 상품이 아닙니다."),
    REQUIRED_IMAGE(HttpStatus.BAD_REQUEST, "이미지는 필수로 등록해야 합니다."),
    UPLOAD_ERROR_IMAGE(HttpStatus.BAD_REQUEST, "이미지 업로드 에러가 발생했습니다."),
    VALID_ERROR_IMAGE(HttpStatus.BAD_REQUEST, "이미지 형식을 확인해주세요."),

    // 옵션
    NOT_FOUND_OPTION(HttpStatus.NOT_FOUND, "존재하지 않는 옵션입니다."),

    // 장바구니
    NOT_FOUND_CART(HttpStatus.NOT_FOUND, "존재하지 않는 장바구니 입니다."),
    CART_NO_PRODUCTS(HttpStatus.NOT_FOUND, "장바구니에 상품이 존재하지 않습니다."),
    CART_IN_GOODS_DUPLICATED(HttpStatus.NOT_FOUND, "장바구니에 이미 담겨있는 상품입니다."),

    // 카테고리
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다"),
    CATEGORY_NAME_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 카테고리 이름입니다."),
    CATEGORY_EXIST_GOODS(HttpStatus.BAD_REQUEST, "카테고리에 속한 상품이 존재합니다."),

    // 리뷰
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하는 리뷰가 없습니다."),
    NOT_MATCH_REVIEW(HttpStatus.BAD_REQUEST, "해당 회원이 작성한 리뷰가 아닙니다."),
    CANT_DELETE_REVIEW(HttpStatus.BAD_REQUEST, "삭제할 수 없는 리뷰입니다."),

    // 대댓글
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_WRITE_REPLY(HttpStatus.BAD_REQUEST, "작성한 댓글이 아닙니다."),

    // 주문
    NOT_FOUND_ORDERS(HttpStatus.NOT_FOUND, "존재하는 주문이 없습니다."),
    NO_BUY_ORDER(HttpStatus.BAD_REQUEST, "구매한 상품이 아닙니다."),

    // 결제
    NOT_FOUND_PAY(HttpStatus.NOT_FOUND, "존재하는 결제가 없습니다."),
    ALREADY_CANCEL_PAY(HttpStatus.BAD_REQUEST, "이미 취소된 결제입니다."),
    CAN_NOT_CANCEL_PAY(HttpStatus.BAD_REQUEST, "결제를 취소할 수 없습니다."),
    NOT_EQUAL_MERCHANT_ID(HttpStatus.BAD_REQUEST, "주문번호 ID가 일치하지 않습니다."),

    //기타
    FAIL_SERIALIZE_OBJECT_INTO_JSON(HttpStatus.BAD_REQUEST, "객체를 JSON으로 변환할 수 없습니다."),
    FAIL_DESERIALIZE_JSON_INTO_OBJECT(HttpStatus.BAD_REQUEST, "JSON을 객체로 변환할 수 없습니다.");




    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;

}
