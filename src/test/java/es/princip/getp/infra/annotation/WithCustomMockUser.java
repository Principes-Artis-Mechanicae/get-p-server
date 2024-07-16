package es.princip.getp.infra.annotation;

import es.princip.getp.domain.member.domain.model.MemberType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static es.princip.getp.domain.member.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.PasswordFixture.PASSWORD;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    long memberId() default 1L;
    String email() default EMAIL;
    String password() default PASSWORD;
    MemberType memberType() default ROLE_PEOPLE;
}
