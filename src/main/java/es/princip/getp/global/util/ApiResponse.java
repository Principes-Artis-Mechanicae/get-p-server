package es.princip.getp.global.util;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

public class ApiResponse {
    public static <T> ApiSuccessResult<T> success(HttpStatus status, T data) {
        return new ApiSuccessResult<>(status.value(), data);
    }

    public static <T> ApiSuccessResult<T> success(HttpStatus status) {
        return new ApiSuccessResult<>(status.value());
    }

    public static <T> ApiErrorResult error(HttpStatus status, String code, String message) {
        return new ApiErrorResult(status.value(), new ApiError(code, message));
    }
    
    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiSuccessResult<T> {
        private final int status;
        private final T data;

        private ApiSuccessResult(int status, T data) {
            this.status = status;
            this.data = data;
        }

        private ApiSuccessResult(int status) {
            this.status = status;
            this.data = null;
        }
    }

    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiError {
        private final String code;
        private final String message;
    
        private ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @JsonInclude(Include.NON_NULL)
    @Getter
    public static class ApiErrorResult {
        private final int status;
        private final ApiError error;

        private ApiErrorResult(int status, ApiError error) {
            this.status = status;
            this.error = error;
        }
    }
}
