package es.princip.getp.domain.member.service;

import es.princip.getp.common.exception.ApiErrorException;
import es.princip.getp.common.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class FailedToSaveProfileImageException extends ApiErrorException {

    private static final String code = "FAILED_TO_SAVE_PROFILE_IMAGE";
    private static final String message = "프로필 사진 저장에 실패했습니다. 다시 시도해주세요.";

    public FailedToSaveProfileImageException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorDescription.of(code, message));
    }
}
