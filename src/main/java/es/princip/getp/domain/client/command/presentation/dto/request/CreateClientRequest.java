package es.princip.getp.domain.client.command.presentation.dto.request;

import es.princip.getp.domain.client.command.application.command.CreateClientCommand;
import es.princip.getp.domain.client.command.domain.Address;
import es.princip.getp.domain.client.command.domain.BankAccount;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(
    @NotBlank String nickname, // 필수
    String email, // 선택, 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotBlank String phoneNumber, // 필수
    @Valid Address address, // 선택
    @Valid BankAccount bankAccount // 선택
) {

    public CreateClientCommand toCommand(final Long memberId) {
        return new CreateClientCommand(
            memberId,
            Nickname.of(nickname()),
            email() == null ? null : Email.of(email()),
            PhoneNumber.of(phoneNumber()),
            address,
            bankAccount
        );
    }
}