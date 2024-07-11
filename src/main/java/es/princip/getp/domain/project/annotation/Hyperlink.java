package es.princip.getp.domain.project.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = "^(https|ftp|mailto|tel)://.*",
    message = "{validation.constraints.Hyperlink.message}"
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = {})
public @interface Hyperlink {
    String message() default "{validation.constraints.Hyperlink.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}