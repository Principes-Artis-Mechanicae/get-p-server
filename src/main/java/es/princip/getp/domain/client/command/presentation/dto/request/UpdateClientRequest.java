package es.princip.getp.domain.client.command.presentation.dto.request;

import es.princip.getp.domain.client.command.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.command.domain.Address;
import es.princip.getp.domain.client.command.domain.BankAccount;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// 갱신 명령은 기존 필드를 모두 가지고 있어야 한다.
public record UpdateClientRequest(
    @NotBlank String nickname,
    @NotBlank String email,
    @NotBlank String phoneNumber,
    @NotNull @Valid Address address,
    @NotNull @Valid BankAccount bankAccount
) {

    public UpdateClientCommand toCommand(final Long memberId) {
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