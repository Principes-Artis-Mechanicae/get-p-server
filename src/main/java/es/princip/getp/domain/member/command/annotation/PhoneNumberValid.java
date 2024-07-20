package es.princip.getp.domain.member.command.annotation;

import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = PhoneNumber.PHONE_REGEX,
    message = "{validation.constraints.PhoneNumber.message}"
)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface PhoneNumberValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}