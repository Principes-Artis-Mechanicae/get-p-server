package es.princip.getp.domain.member.command.annotation;

import es.princip.getp.domain.member.command.domain.model.Email;
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
public @interface EmailValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}