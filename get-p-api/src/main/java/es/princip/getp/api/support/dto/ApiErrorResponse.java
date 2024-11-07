package es.princip.getp.api.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorResponse {

    public static ResponseEntity<ApiErrorResult> error(
        final HttpStatus status,
        final ErrorDescription... descriptions
    ) {
        final ApiErrorResult result = new ApiErrorResult(status.value(), descriptions);
        return ResponseEntity.status(status).body(result);
    }

    @Getter
    @JsonInclude(Include.NON_NULL)
    public static class ApiErrorResult {

        private final int status;
        private final ErrorDescription[] errors;

        private ApiErrorResult(final int status, final ErrorDescription... descriptions) {
            this.status = status;
            this.errors = descriptions;
        }
    }
}
