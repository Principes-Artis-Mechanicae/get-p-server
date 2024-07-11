package es.princip.getp.domain.member.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = "^[^-]*$",
    message = "{validation.constraints.PhoneNumber.message}"
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = {})
public @interface PhoneNumber {
    String message() default "{validation.constraints.PhoneNumber.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}