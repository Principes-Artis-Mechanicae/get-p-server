package es.princip.getp.domain.member.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserMemberTypeValidator.class})
public @interface UserMemberType {
    String message() default "{validation.constraints.UserMemberType.message}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
