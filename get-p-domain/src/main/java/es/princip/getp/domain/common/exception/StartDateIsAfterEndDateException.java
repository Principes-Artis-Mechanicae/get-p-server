package es.princip.getp.domain.common.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class StartDateIsAfterEndDateException extends DomainLogicException {

    private static final String code = "START_DATE_IS_AFTER_END_DATE";
    private static final String message = "시작일이 종료일보다 늦을 수 없습니다.";

    public StartDateIsAfterEndDateException() {
        super(ErrorDescription.of(code, message));
    }
}
