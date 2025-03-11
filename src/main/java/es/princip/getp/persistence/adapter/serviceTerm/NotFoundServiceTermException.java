package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

import java.util.Set;

class NotFoundServiceTermException extends NotFoundException {

    private static final String code = "NOT_FOUND_SERVICE_TERM";
    private static final String message = "존재하지 않는 서비스 약관입니다.";

    NotFoundServiceTermException(final Set<String> tags) {
        super(ErrorDescription.of(code, formatMessage(tags)));
    }

    static String formatMessage(final Set<String> tags) {
        return String.format("%s은(는) %s", String.join(", ", tags), message);
    }
}
