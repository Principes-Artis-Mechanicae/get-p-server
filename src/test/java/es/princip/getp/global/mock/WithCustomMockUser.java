package es.princip.getp.global.mock;

import es.princip.getp.domain.member.domain.MemberType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    long memberId() default 1L;
    String email() default "test@example.com";
    MemberType memberType() default MemberType.ROLE_PEOPLE;
}
