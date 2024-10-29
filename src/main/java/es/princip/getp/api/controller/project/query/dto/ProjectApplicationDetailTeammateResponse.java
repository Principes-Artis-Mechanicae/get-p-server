package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.domain.project.apply.model.TeammateStatus;

public record ProjectApplicationDetailTeammateResponse(
    Long peopleId,
    String nickname,
    TeammateStatus status,
    String profileImageUrl
) {
}