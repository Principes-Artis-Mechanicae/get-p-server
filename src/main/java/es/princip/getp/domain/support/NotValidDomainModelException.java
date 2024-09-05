package es.princip.getp.domain.support;

public class NotValidDomainModelException extends DomainLogicException {

    private static final String code = "NOT_VALID_DOMAIN_MODEL";

    public NotValidDomainModelException(final String message) {
        super(ErrorDescription.of(code, message));
    }
}
