package com.example.jolvre.auth.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class EmailDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class EmailSendResponse {
        private String email;
        private String authNum;
    }

    @Getter
    @AllArgsConstructor
    public static class EmailVerifyRequest {
        @Email
        @NotEmpty(message = "이메일을 입력해 주세요")
        private String email;

        @NotEmpty(message = "인증 번호를 입력해 주세요")
        private String authNum;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class EmailVerifyResponse {
        private String email;

        private boolean verifyResult;
    }

}
