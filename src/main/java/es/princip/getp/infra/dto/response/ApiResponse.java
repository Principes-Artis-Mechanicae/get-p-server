package es.princip.getp.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiResponse {
    public static <T> ApiSuccessResult<T> success(HttpStatus status, T data) {
        return new ApiSuccessResult<>(status.value(), data);
    }

    public static <T> ApiSuccessResult<T> success(HttpStatus status, String message) {
        return new ApiSuccessResult<>(status.value(), message);
    }

    public static <T> ApiSuccessResult<T> success(HttpStatus status) {
        return new ApiSuccessResult<>(status.value());
    }

    public static ApiErrorResult error(ErrorCode errorCode) {
        return new ApiErrorResult(errorCode);
    }

    public static ApiErrorResult error(HttpStatus status, ErrorDescription... descriptions) {
        return new ApiErrorResult(status.value(), descriptions);
    }
    
    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiSuccessResult<T> {
        private final int status;
        private final T data;
        private final String message;

        private ApiSuccessResult(int status, T data) {
            this.status = status;
            this.data = data;
            this.message = null;
        }

        private ApiSuccessResult(int status) {
            this.status = status;
            this.data = null;
            this.message = null;
        }

        private ApiSuccessResult(int status, String message) {
            this.status = status;
            this.data = null;
            this.message = message;
        }
    }

    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiErrorResult {
        private final int status;
        private final ErrorDescription[] errors;

        private ApiErrorResult(ErrorCode errorCode) {
            this.status = errorCode.status().value();
            this.errors = new ErrorDescription[] { errorCode.description() };
        }

        private ApiErrorResult(int status, ErrorDescription... descriptions) {
            this.status = status;
            this.errors = descriptions;
        }
    }
}
