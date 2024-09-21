package es.princip.getp.api.controller.people.query.dto.people;

import java.time.LocalDateTime;

public record MyPeopleResponse(
    Long peopleId,
    String email,
    String nickname,
    String phoneNumber,
    String profileImageUri,
    Integer completedProjectsCount,
    Integer likesCount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}