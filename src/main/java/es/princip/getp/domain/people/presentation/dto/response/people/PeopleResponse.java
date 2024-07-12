package es.princip.getp.domain.people.presentation.dto.response.people;

import es.princip.getp.domain.member.domain.model.Member;
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

    public static PeopleResponse from(People people, Member member) {
        return new PeopleResponse(
            people.getPeopleId(),
            member.getNickname().getValue(),
            people.getEmail().getValue(),
            member.getPhoneNumber().getValue(),
            people.getPeopleType(),
            member.getProfileImage().getUri().toString(),
            people.getCreatedAt(),
            people.getUpdatedAt()
        );
    }
}