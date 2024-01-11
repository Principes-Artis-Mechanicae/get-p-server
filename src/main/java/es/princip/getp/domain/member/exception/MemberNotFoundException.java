package es.princip.getp.domain.member.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class MemberNotFoundException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String code = "MEMBER_NOT_FOUND";
    private static final String message = "존재하지 않는 회원";
    
    public MemberNotFoundException() {
        super(status, code, message);
    }
}