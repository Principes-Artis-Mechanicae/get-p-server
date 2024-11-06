package es.princip.getp.persistence.support;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;

public class SerializeCursorException extends ErrorDescriptionException {

    private static final String code = "SERIALIZE_CURSOR_ERROR";
    private static final String message = "커서를 직렬화하는데 오류가 발생했습니다.";

    public SerializeCursorException() {
        super(ErrorDescription.of(code, message));
    }
}