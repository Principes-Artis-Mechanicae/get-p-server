package es.princip.getp.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static <T> ApiSuccessResult<T> body(final HttpStatus status, final T data) {
        return new ApiSuccessResult<>(status.value(), data);
    }

    public static <T> ApiSuccessResult<T> body(final HttpStatus status) {
        return new ApiSuccessResult<>(status.value());
    }

    public static <T> ResponseEntity<ApiSuccessResult<T>> success(final HttpStatus status, final T data) {
        return ResponseEntity.status(status).body(ApiResponse.body(status, data));
    }

    public static ResponseEntity<ApiSuccessResult<?>> success(final HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.body(status));
    }

    public static ResponseEntity<ApiErrorResult> error(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.status())
            .body(new ApiErrorResult(errorCode));
    }

    public static ResponseEntity<ApiErrorResult> error(
        final HttpStatus status,
        final ErrorDescription... descriptions
    ) {
        return ResponseEntity.status(status)
            .body(new ApiErrorResult(status.value(), descriptions));
    }
    
    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiSuccessResult<T> {
        private final int status;
        private final T data;

        private ApiSuccessResult(final int status, final T data) {
            this.status = status;
            this.data = data;
        }

        private ApiSuccessResult(final int status) {
            this.status = status;
            this.data = null;
        }
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
