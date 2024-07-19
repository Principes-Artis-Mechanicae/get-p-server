package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.command.domain.model.Nickname;

public class NicknameFixture {
    public static final String NICKNAME = "nickname";

    public static Nickname nickname() {
        return Nickname.of(NICKNAME);
    }
}
