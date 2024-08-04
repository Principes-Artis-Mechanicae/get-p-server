package es.princip.getp.domain.common.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class StartTimeIsAfterEndTimeException extends BusinessLogicException{

    public StartTimeIsAfterEndTimeException() {
        super("시작 시간이 종료 시간보다 늦을 수 없습니다.");
    }
    
}
