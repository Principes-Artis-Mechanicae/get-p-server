package es.princip.getp.global.exception;

public record ErrorDescription(
    String code,
    String message
    ) {

    public static ErrorDescription of(String code, String message) {
        return new ErrorDescription(code, message);
    }
}