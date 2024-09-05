package es.princip.getp.domain.member.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotAgreedAllRequiredServiceTermException extends DomainLogicException {

    private static final String code = "NOT_AGREED_ALL_REQUIRED_SERVICE_TERM";
    private static final String message = "모든 필수 서비스 약관에 동의하지 않았습니다.";

    public NotAgreedAllRequiredServiceTermException() {
        super(ErrorDescription.of(code, message));
    }
}
