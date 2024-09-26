package es.princip.getp.api.controller.project.query.dto;

public record SearchTeammateResponse(
    Long peopleId,
    String nickname,
    String profileImageUri
) {
}
