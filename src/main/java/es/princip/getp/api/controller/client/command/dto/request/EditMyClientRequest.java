package es.princip.getp.api.controller.client.command.dto.request;

import es.princip.getp.domain.client.command.application.command.EditClientCommand;
import es.princip.getp.domain.client.command.domain.Address;
import es.princip.getp.domain.client.command.domain.BankAccount;
import es.princip.getp.domain.member.command.annotation.EmailPattern;
import es.princip.getp.domain.member.command.annotation.PhoneNumberPattern;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// 갱신 명령은 기존 필드를 모두 가지고 있어야 한다.
public record EditMyClientRequest(
    @NotBlank String nickname,
    @NotNull @EmailPattern String email,
    @NotNull @PhoneNumberPattern String phoneNumber,
    @NotNull @Valid Address address,
    @NotNull @Valid BankAccount bankAccount
) {

    public EditClientCommand toCommand(final Long memberId) {
        return new EditClientCommand(
            memberId,
            Nickname.of(nickname()),
            Email.of(email()),
            PhoneNumber.of(phoneNumber()),
            address,
            bankAccount
        );
    }
}