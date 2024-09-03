package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.Nickname;

public class NicknameFixture {
    public static final String NICKNAME = "닉네임";

    public static Nickname nickname() {
        return Nickname.from(NICKNAME);
    }
}
