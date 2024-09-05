package es.princip.getp.domain.member.model;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = Password.PASSWORD_REGEX,
    message = "{validation.constraints.PhoneNumber.message}"
)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface PasswordPattern {

    String message() default "{validation.constraints.Password.message}"; // Spring Rest Docs에서 사용

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}