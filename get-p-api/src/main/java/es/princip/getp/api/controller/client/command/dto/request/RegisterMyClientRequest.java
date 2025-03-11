package es.princip.getp.api.controller.client.command.dto.request;

import es.princip.getp.application.client.dto.command.RegisterClientCommand;
import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.EmailPattern;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.common.model.PhoneNumberPattern;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.Nickname;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterMyClientRequest(
    @NotBlank String nickname, // 필수
    @EmailPattern String email, // 선택, 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    @NotNull @PhoneNumberPattern String phoneNumber, // 필수
    @Valid Address address // 선택
) {

    public RegisterClientCommand toCommand(final Member member) {
        return new RegisterClientCommand(
            member,
            Nickname.from(nickname()),
            email() == null ? member.getEmail() : Email.from(email()),
            PhoneNumber.from(phoneNumber()),
            address
        );
    }
}