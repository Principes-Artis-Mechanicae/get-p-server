package es.princip.getp.application.serviceTerm.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.application.support.NotFoundException;

import java.util.Set;

public class NotFoundServiceTermException extends NotFoundException {

    private static final String code = "NOT_FOUND_SERVICE_TERM";
    private static final String message = "존재하지 않는 서비스 약관입니다.";

    public NotFoundServiceTermException(final Set<String> tags) {
        super(ErrorDescription.of(code, formatMessage(tags)));
    }

    public static String formatMessage(final Set<String> tags) {
        return String.format("%s은(는) %s", String.join(", ", tags), message);
    }
}
