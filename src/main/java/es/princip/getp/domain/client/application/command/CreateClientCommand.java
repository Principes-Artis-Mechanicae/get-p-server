package es.princip.getp.domain.client.application.command;

import es.princip.getp.domain.client.domain.Address;
import es.princip.getp.domain.client.domain.BankAccount;
import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;

public record CreateClientCommand(
    Long memberId,
    Nickname nickname,
    Email email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    PhoneNumber phoneNumber,
    Address address,
    BankAccount bankAccount
) {
}
