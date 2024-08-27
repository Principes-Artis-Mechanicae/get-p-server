package es.princip.getp.api.security.annotation;

import es.princip.getp.domain.member.model.MemberType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.member.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    long memberId() default 1L;
    String nickname() default NICKNAME;
    String phoneNumber() default PHONE_NUMBER;
    String email() default EMAIL;
    String password() default PASSWORD;
    MemberType memberType() default ROLE_PEOPLE;
}
