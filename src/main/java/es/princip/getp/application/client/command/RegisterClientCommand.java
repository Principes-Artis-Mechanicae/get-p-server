package es.princip.getp.application.client.command;

import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.Nickname;

public record RegisterClientCommand(
    Member member,
    Nickname nickname,
    Email email, // 미입력 시 회원 가입 시 작성한 이메일 주소가 기본값
    PhoneNumber phoneNumber,
    Address address
) {
}
