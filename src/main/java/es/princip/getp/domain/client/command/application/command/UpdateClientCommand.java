package es.princip.getp.domain.client.command.application.command;

import es.princip.getp.domain.client.command.domain.Address;
import es.princip.getp.domain.client.command.domain.BankAccount;
import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;

public record UpdateClientCommand(
    Long memberId,
    Nickname nickname,
    Email email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    PhoneNumber phoneNumber,
    Address address,
    BankAccount bankAccount
) {
}
