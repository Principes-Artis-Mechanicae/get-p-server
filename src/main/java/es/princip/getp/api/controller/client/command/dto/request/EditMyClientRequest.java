package es.princip.getp.api.controller.client.command.dto.request;

import es.princip.getp.application.client.command.EditClientCommand;
import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.client.model.BankAccount;
import es.princip.getp.api.validation.EmailPattern;
import es.princip.getp.api.validation.PhoneNumberPattern;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.common.model.PhoneNumber;
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