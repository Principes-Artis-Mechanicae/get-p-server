package es.princip.getp.application.member.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotSupportedProfileImageExtensionException extends ApplicationLogicException {

    private static final String code = "NOT_SUPPORTED_PROFILE_IMAGE_EXTENSION";
    private static final String message = """
        지원하지 않는 프로필 사진 확장자입니다. '.jpg', '.png', '.bmp'로 다시 시도해주세요.
        """;

    public NotSupportedProfileImageExtensionException() {
        super(ErrorDescription.of(code, message));
    }
}
