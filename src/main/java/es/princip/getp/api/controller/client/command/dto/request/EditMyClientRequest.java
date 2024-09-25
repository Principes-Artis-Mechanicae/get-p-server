package es.princip.getp.api.controller.client.command.dto.request;

import es.princip.getp.application.client.command.EditClientCommand;
import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.EmailPattern;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.common.model.PhoneNumberPattern;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.Nickname;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// 갱신 명령은 기존 필드를 모두 가지고 있어야 한다.
public record EditMyClientRequest(
    @NotBlank String nickname,
    @NotNull @EmailPattern String email,
    @NotNull @PhoneNumberPattern String phoneNumber,
    @NotNull @Valid Address address
) {

    public EditClientCommand toCommand(final MemberId memberId) {
        return new EditClientCommand(
            memberId,
            Nickname.from(nickname()),
            Email.from(email()),
            PhoneNumber.from(phoneNumber()),
            address
        );
    }
}