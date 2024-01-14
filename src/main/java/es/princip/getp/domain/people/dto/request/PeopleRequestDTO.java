package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleRoleType;
import jakarta.validation.constraints.NotNull;

public record PeopleRequestDTO(@NotNull String name, @NotNull String email,
        @NotNull String phoneNumber, @NotNull String roleType, @NotNull String profileImageUri,
        @NotNull String accountNumber) {
    public People toEntity() {
        return People.builder()
            .name(name)
            .email(email)
            .phoneNumber(phoneNumber)
            .roleType(PeopleRoleType.valueOf(roleType))
            .profileImageUri(profileImageUri)
            .accountNumber(accountNumber)
            .build();
    }
}
