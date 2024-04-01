package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import java.time.LocalDateTime;

public record UpdatePeopleResponse(
    Long peopleId,
    String nickname,
    String email,
    String phoneNumber,
    PeopleType peopleType,
    String profileImageUri,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static UpdatePeopleResponse from(People people) {
        return new UpdatePeopleResponse(
            people.getPeopleId(),
            people.getNickname(),
            people.getEmail(),
            people.getPhoneNumber(),
            people.getPeopleType(),
            people.getProfileImageUri(),
            people.getCreatedAt(),
            people.getUpdateAt()
        );
    }
}