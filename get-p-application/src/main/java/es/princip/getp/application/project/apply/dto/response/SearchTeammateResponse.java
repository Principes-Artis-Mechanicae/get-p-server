package es.princip.getp.application.project.apply.dto.response;

public record SearchTeammateResponse(
    Long peopleId,
    String nickname,
    String profileImageUri
) {
}
