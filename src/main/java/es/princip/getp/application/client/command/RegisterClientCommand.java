package es.princip.getp.application.client.command;

import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.client.model.BankAccount;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.member.model.PhoneNumber;

public record RegisterClientCommand(
    Long memberId,
    Nickname nickname,
    Email email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    PhoneNumber phoneNumber,
    Address address,
    BankAccount bankAccount
) {
}
