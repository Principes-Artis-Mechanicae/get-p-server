package es.princip.getp.application.project.apply.dto.response;

public record ProjectApplicantTeammateResponse(
    Long peopleId,
    String nickname,
    String profileImageUrl
) {
}