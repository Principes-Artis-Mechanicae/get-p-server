package es.princip.getp.api.controller.project.query.dto;

public record ProjectApplicantTeammateResponse(
    Long peopleId,
    String nickname,
    String profileImageUrl
) {
}