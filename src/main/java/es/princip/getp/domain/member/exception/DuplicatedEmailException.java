package es.princip.getp.domain.member.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class DuplicatedEmailException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "DUPLICATED_EMAIL";
    private static final String message = "사용자가 이미 가입된 이메일로 회원 가입 요청";
    
    public DuplicatedEmailException() {
        super(status, code, message);
    }
}