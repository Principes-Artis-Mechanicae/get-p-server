package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.NotFoundException;

// TODO: 접근 제어자를 private-default로 변경
public class NotFoundServiceTermException extends NotFoundException {

    private static final String code = "NOT_FOUND_SERVICE_TERM";
    private static final String message = "존재하지 않는 서비스 약관입니다.";

    public NotFoundServiceTermException() {
        super(ErrorDescription.of(code, message));
    }
}
