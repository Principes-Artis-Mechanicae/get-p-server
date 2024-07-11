package es.princip.getp.domain.client.dto.request;

import es.princip.getp.global.domain.Address;
import es.princip.getp.global.domain.BankAccount;
import es.princip.getp.global.validator.annotation.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(
    @NotBlank String nickname,
    @Email String email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotBlank @PhoneNumber String phoneNumber,
    String profileImageUri,
    @Valid Address address,
    @Valid BankAccount bankAccount) {

}