package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

public class NotFoundProjectApplicationException extends NotFoundException {

    private static final String code = "NOT_FOUND_PROJECT_APPLICATION";
    private static final String message = "존재하지 않는 프로젝트 지원 내역입니다.";

    public NotFoundProjectApplicationException() {
        super(ErrorDescription.of(code, message));
    }
}
