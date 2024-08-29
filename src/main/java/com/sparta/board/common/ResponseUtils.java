package com.sparta.board.common;

import com.sparta.board.entity.enumSet.ErrorType;

import java.util.List;

public class ResponseUtils {

    // 요청 성공인 경우
    public static <T> ApiResponseDto<T> ok(T response) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .response(response)
                .build();
    }

    // 검색 결과가 존재하지 않는 경우
    public static ApiResponseDto<List<String>> okWithMessage(List<String> data, ErrorType errorType) {
        // 새로운 ApiResponseDto 인스턴스를 생성하여 반환합니다.
        return ApiResponseDto.<List<String>>builder()
                .success(true)
                .response(data)
                .error(ErrorResponse.of(errorType)) // ErrorResponse를 올바르게 생성합니다.
                .build();
    }

    // 에러 발생한 경우
    public static <T> ApiResponseDto<T> error(ErrorResponse response) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .error(response)
                .build();
    }

}
