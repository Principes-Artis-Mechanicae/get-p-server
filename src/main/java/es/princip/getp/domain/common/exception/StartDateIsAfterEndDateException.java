package es.princip.getp.domain.common.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class StartDateIsAfterEndDateException extends BusinessLogicException {

    private static final String code = "START_DATE_IS_AFTER_END_DATE";
    private static final String message = "시작일이 종료일보다 늦을 수 없습니다.";

    public StartDateIsAfterEndDateException() {
        super(ErrorDescription.of(code, message));
    }
}
