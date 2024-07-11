package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;

import java.time.LocalDateTime;

public record PeopleResponse(
    Long peopleId,
    String nickname,
    String email,
    String phoneNumber,
    PeopleType peopleType,
    String profileImageUri,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static PeopleResponse from(People people) {
        return new PeopleResponse(
            people.getPeopleId(),
            people.getNickname(),
            people.getEmail(),
            people.getPhoneNumber(),
            people.getPeopleType(),
            people.getProfileImageUri(),
            people.getCreatedAt(),
            people.getUpdatedAt()
        );
    }
}