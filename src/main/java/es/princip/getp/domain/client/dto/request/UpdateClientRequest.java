package es.princip.getp.domain.client.dto.request;

import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record UpdateClientRequest(@NotNull String nickname, @NotNull String email,
        @NotNull String phoneNumber, @NotNull String profileImageUri, @NotNull String address,
        @NotNull String accountNumber) {
    public Client toEntity(final Member member) {
        return Client.builder()
            .nickname(nickname)
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUri(profileImageUri)
            .address(address)
            .accountNumber(accountNumber)
            .member(member)
            .build();
    }
}