package es.princip.getp.common.annotation;

import es.princip.getp.common.domain.URL;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
    regexp = URL.URL_REGEX,
    message = "{validation.constraints.URL.message}"
)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface URLPattern {

    String message() default "{validation.constraints.URL.message}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}