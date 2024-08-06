package es.princip.getp.domain.people.query.dto.people;

import es.princip.getp.domain.people.command.domain.PeopleType;

import java.time.LocalDateTime;

public record MyPeopleResponse(
    Long peopleId,
    String email,
    String nickname,
    String phoneNumber,
    String profileImageUri,
    PeopleType peopleType,
    Integer completedProjectsCount,
    Integer likesCount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}