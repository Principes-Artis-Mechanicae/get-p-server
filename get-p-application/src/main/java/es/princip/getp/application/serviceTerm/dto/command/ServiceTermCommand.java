package es.princip.getp.application.serviceTerm.dto.command;

public record ServiceTermCommand(
    String tag,
    boolean required, 
    boolean revocable
) {
}