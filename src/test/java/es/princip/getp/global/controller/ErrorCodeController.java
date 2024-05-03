package es.princip.getp.global.controller;

import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/error-code")
public class ErrorCodeController {

    @GetMapping("/signup")
    public Map<String, ErrorCodeResponse> getSignUpErrorCode() {
        Map<String, ErrorCodeResponse> map = new HashMap<>();
        for (ErrorCode errorCode : SignUpErrorCode.values()) {
            map.put(errorCode.description().code(), ErrorCodeResponse.from(errorCode));
        }
        return map;
    }

    @Getter
    @AllArgsConstructor
    static class ErrorCodeResponse {
        private String code;
        private String message;
        private int status;

        public static ErrorCodeResponse from(ErrorCode errorCode) {
            return new ErrorCodeResponse(
                errorCode.description().code(),
                errorCode.description().message(),
                errorCode.status().value()
            );
        }
    }
}