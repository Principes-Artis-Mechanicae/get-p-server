package es.princip.getp.infra.presentation;

import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.storage.exception.ImageErrorCode;
import es.princip.getp.infra.dto.response.ErrorCodeResponse;
import es.princip.getp.infra.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/error-code")
public class ErrorCodeController {

    @GetMapping("/login")
    public Map<String, ErrorCodeResponse> getLoginErrorCode() {
        Map<String, ErrorCodeResponse> map = new HashMap<>();
        for (ErrorCode errorCode : LoginErrorCode.values()) {
            map.put(errorCode.description().code(), ErrorCodeResponse.from(errorCode));
        }
        return map;
    }

    @GetMapping("/signup")
    public Map<String, ErrorCodeResponse> getSignUpErrorCode() {
        Map<String, ErrorCodeResponse> map = new HashMap<>();
        for (ErrorCode errorCode : SignUpErrorCode.values()) {
            map.put(errorCode.description().code(), ErrorCodeResponse.from(errorCode));
        }
        return map;
    }

    @GetMapping("/storage/images")
    public Map<String, ErrorCodeResponse> getImageStorageErrorCode() {
        Map<String, ErrorCodeResponse> map = new HashMap<>();
        for (ErrorCode errorCode : ImageErrorCode.values()) {
            map.put(errorCode.description().code(), ErrorCodeResponse.from(errorCode));
        }
        return map;
    }
}