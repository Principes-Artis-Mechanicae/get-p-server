package es.princip.getp.domain.client.dto.request;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.member.domain.entity.Member;
import jakarta.validation.constraints.NotNull;

public record CreateClientRequest(
        @NotNull String nickname, 
        String email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
        @NotNull String phoneNumber, 
        String profileImageUri, 
        @NotNull String address,
        @NotNull String accountNumber) {
    public Client toEntity(final Member member) {
        return Client.builder()
            .nickname(nickname)
            .email(email == null ? member.getEmail() : email)
            .phoneNumber(phoneNumber)
            .profileImageUri(profileImageUri)
            .address(address)
            .accountNumber(accountNumber)
            .member(member)
            .build();
    }
}