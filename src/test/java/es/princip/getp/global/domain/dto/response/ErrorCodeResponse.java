package es.princip.getp.global.domain.dto.response;

import es.princip.getp.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorCodeResponse {
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