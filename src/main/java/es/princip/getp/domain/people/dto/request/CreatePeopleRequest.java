package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleRequest(@NotNull String nickname, @NotNull String email,
                                  @NotNull String phoneNumber, @NotNull String peopleType,
                                  @NotNull String profileImageUri, @NotNull String accountNumber) {

    public People toEntity(final Member member) {
        return People.builder()
            .nickname(nickname)
            .email(email)
            .phoneNumber(phoneNumber)
            .peopleType(PeopleType.valueOf(peopleType))
            .profileImageUri(profileImageUri)
            .accountNumber(accountNumber)
            .member(member)
            .build();
    }
}