package com.spamallday.payhere.exception;

public enum CustomErrorCode {
    BAD_REQUEST_ERROR("잘못된 요청입니다."),

    // 회원
    PHONE_NUM_ERROR("잘못된 휴대폰 번호입니다."),
    PHONE_NUM_DUP_ERROR("중복된 휴대폰 번호입니다."),
    SIGNUP_ERROR("회원가입에 문제가 발생했습니다."),
    NO_USER_ERROR("해당하는 유저가 없습니다."),

    // 인증
    TOKEN_UN_AUTH_ERROR("권한 정보가 없는 토큰입니다."),
    TOKEN_VALID_ERROR("유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED_ERROR("만료된 토큰입니다."),
    TOKEN_UN_SUPPORT_ERROR("지원하지 않는 토큰입니다."),
    TOKEN_EMPTY_CLAIM_ERROR("올바르지 않은 Claim입니다."),
    TOKEN_REFRESH_VALID_ERROR("Refresh token이 유효하지 않습니다."),
    TOKEN_REFRESH_NOT_EQUAL_ERROR("Refresh token이 일치하지 않습니다."),
    TOKEN_CLAIM_ERROR("id가 포함되지 않았습니다."),

    // 상품
    BARCODE_ERROR("잘못된 바코드 형식입니다."),
    EXPIRE_PASS_ERROR("이미 지난 유통기한입니다."),
    EXPIRE_TYPE_ERROR("잘못된 유통기한입니다."),
    SIZE_WRONG_ERROR("잘못된 사이즈입니다."),
    PRODUCT_UPDATE_ERROR("상품 수정에 문제가 발생했습니다."),
    PRODUCT_REG_ERROR("상품 등록에 문제가 발생했습니다."),
    PRODUCT_DELETE_ERROR("상품 삭제 중 문제가 발생했습니다."),
    PRODUCT_NO_ITEM_ERROR("일치하는 상품이 없습니다."),
    SEARCH_KEY_ERROR("검색 키워드가 올바르지 않습니다.");

    private final String errorMsg;

    CustomErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
