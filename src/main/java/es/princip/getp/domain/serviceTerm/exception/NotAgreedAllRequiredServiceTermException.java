package es.princip.getp.domain.serviceTerm.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class NotAgreedAllRequiredServiceTermException extends BusinessLogicException {

    public NotAgreedAllRequiredServiceTermException() {
        super("모든 필수 서비스 약관에 동의하지 않았습니다.");
    }
}
