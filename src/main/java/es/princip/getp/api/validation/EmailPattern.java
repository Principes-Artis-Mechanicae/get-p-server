package es.princip.getp.api.validation;

import es.princip.getp.domain.member.model.Email;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = Email.EMAIL_REGEX,
    message = "{validation.constraints.Email.message}"
)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface EmailPattern {

    String message() default "{validation.constraints.Email.message}"; // Spring Rest Docs에서 사용

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}