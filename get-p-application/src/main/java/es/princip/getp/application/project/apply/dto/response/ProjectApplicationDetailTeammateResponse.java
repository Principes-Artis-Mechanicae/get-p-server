package es.princip.getp.application.project.apply.dto.response;

import es.princip.getp.domain.project.apply.model.TeammateStatus;

public record ProjectApplicationDetailTeammateResponse(
    Long peopleId,
    String nickname,
    TeammateStatus status,
    String profileImageUrl
) {
}