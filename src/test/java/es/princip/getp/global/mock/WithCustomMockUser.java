package es.princip.getp.global.mock;

import es.princip.getp.domain.member.entity.MemberType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    String email() default "test@example.com";
    MemberType memberType() default MemberType.ROLE_PEOPLE;
}
