package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.model.Nickname;

public class NicknameFixture {
    public static final String NICKNAME = "닉네임";

    public static Nickname nickname() {
        return Nickname.of(NICKNAME);
    }
}
