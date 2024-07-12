package es.princip.getp.domain.member.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = es.princip.getp.domain.member.domain.model.Password.PASSWORD_REGEX,
    message = "{validation.constraints.Password.message}"
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
public @interface Password {
    String message() default "{validation.constraints.Password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}