package es.princip.getp.api.controller.client.command.dto.request;

import es.princip.getp.application.client.command.RegisterClientCommand;
import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.client.model.BankAccount;
import es.princip.getp.api.validation.EmailPattern;
import es.princip.getp.api.validation.PhoneNumberPattern;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.member.model.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterMyClientRequest(
    @NotBlank String nickname, // 필수
    @EmailPattern String email, // 선택, 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotNull @PhoneNumberPattern String phoneNumber, // 필수
    @Valid Address address, // 선택
    @Valid BankAccount bankAccount // 선택
) {

    public RegisterClientCommand toCommand(final Long memberId) {
        return new RegisterClientCommand(
            memberId,
            Nickname.of(nickname()),
            email() == null ? null : Email.of(email()),
            PhoneNumber.of(phoneNumber()),
            address,
            bankAccount
        );
    }
}