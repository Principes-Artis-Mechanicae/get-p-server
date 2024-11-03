package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.api.support.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@Order(1)
@RestControllerAdvice
public class MaxUploadSizeExceededExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResult> handle(final MaxUploadSizeExceededException exception) {
        log.debug(exception.getMessage(), exception);
        final ErrorDescription description = ErrorDescription.of(
            "MAX_UPLOAD_SIZE_EXCEEDED",
            "최대 허용 파일 크기를 초과했습니다.   "
        );
        return ApiErrorResponse.error(HttpStatus.PAYLOAD_TOO_LARGE, description);
    }
}