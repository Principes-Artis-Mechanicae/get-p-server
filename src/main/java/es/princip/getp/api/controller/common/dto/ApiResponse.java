package es.princip.getp.api.controller.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
}
