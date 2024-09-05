package es.princip.getp.api.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.princip.getp.common.exception.ErrorCode;
import es.princip.getp.common.exception.ErrorDescription;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorResponse {

    public static ApiErrorResult body(final HttpStatus status, final ErrorDescription... descriptions) {
        return new ApiErrorResult(status.value(), descriptions);
    }

    public static ApiErrorResult body(final ErrorCode errorCode) {
        return new ApiErrorResult(errorCode);
    }

    public static ResponseEntity<ApiErrorResult> error(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.status()).body(ApiErrorResponse.body(errorCode));
    }

    public static ResponseEntity<ApiErrorResult> error(
        final HttpStatus status,
        final ErrorDescription... descriptions
    ) {
        return ResponseEntity.status(status).body(ApiErrorResponse.body(status, descriptions));
    }

    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiErrorResult {
        private final int status;
        private final ErrorDescription[] errors;

        private ApiErrorResult(final ErrorCode errorCode) {
            this.status = errorCode.status().value();
            this.errors = new ErrorDescription[] { errorCode.description() };
        }

        private ApiErrorResult(final int status, final ErrorDescription... descriptions) {
            this.status = status;
            this.errors = descriptions;
        }
    }
}
