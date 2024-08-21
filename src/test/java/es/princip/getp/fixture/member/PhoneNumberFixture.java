package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.PhoneNumber;

public class PhoneNumberFixture {
    public static final String PHONE_NUMBER = "01012345678";

    public static PhoneNumber phoneNumber() {
        return PhoneNumber.of(PHONE_NUMBER);
    }
}
