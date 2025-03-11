package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

public class NotFoundProjectException extends NotFoundException {

    private static final String code = "NOT_FOUND_PROJECT";
    private static final String message = "존재하지 않는 프로젝트입니다.";

    public NotFoundProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
