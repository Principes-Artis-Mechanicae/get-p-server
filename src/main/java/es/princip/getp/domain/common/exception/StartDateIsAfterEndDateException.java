package es.princip.getp.domain.common.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class StartDateIsAfterEndDateException extends BusinessLogicException {

    public StartDateIsAfterEndDateException() {
        super("시작일이 종료일보다 늦을 수 없습니다.");
    }
}
