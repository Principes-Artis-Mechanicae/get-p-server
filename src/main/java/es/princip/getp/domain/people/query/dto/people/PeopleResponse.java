package es.princip.getp.domain.people.query.dto.people;

import es.princip.getp.domain.people.command.domain.PeopleType;

import java.time.LocalDateTime;

public record PeopleResponse(
    Long peopleId,
    String email,
    String nickname,
    String profileImageUri,
    PeopleType peopleType,
    Integer completedProjectsCount,
    Integer interestsCount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}