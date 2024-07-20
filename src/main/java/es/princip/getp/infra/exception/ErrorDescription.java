package es.princip.getp.infra.exception;

public record ErrorDescription(
    String code,
    String message
) {

    public static ErrorDescription of(final String code, final String message) {
        return new ErrorDescription(code, message);
    }
}