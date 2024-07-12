package es.princip.getp.domain.client.presentation.dto.request;

import es.princip.getp.domain.client.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.domain.Address;
import es.princip.getp.domain.client.domain.BankAccount;
import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateClientRequest(
    @NotBlank String nickname,
    @NotNull String email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotBlank String phoneNumber,
    @Valid Address address,
    @Valid BankAccount bankAccount
) {

    public UpdateClientCommand toCommand(Long memberId) {
        return new UpdateClientCommand(
            memberId,
            Nickname.of(nickname()),
            Email.of(email()),
            PhoneNumber.of(phoneNumber()),
            address,
            bankAccount
        );
    }
}