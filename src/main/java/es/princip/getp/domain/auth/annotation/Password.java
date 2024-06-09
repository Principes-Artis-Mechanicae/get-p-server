package es.princip.getp.domain.auth.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_+=/])[A-Za-z\\d!@#$%^&*_+=/]{8,20}$",
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