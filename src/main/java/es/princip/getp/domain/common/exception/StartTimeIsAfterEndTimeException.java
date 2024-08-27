package es.princip.getp.domain.common.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class StartTimeIsAfterEndTimeException extends BusinessLogicException {

    private static final String code = "START_TIME_IS_AFTER_END_TIME";
    private static final String message = "시작 시간이 종료 시간보다 늦을 수 없습니다.";

    public StartTimeIsAfterEndTimeException() {
        super(ErrorDescription.of(code, message));
    }
}
