package es.princip.getp.domain.client.dto.request;

import es.princip.getp.domain.client.domain.Address;
import es.princip.getp.domain.client.domain.BankAccount;
import es.princip.getp.domain.member.annotation.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateClientRequest(
    @NotBlank String nickname,
    @Email String email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotBlank @PhoneNumber String phoneNumber,
    String profileImageUri,
    @Valid Address address,
    @Valid BankAccount bankAccount) {
}