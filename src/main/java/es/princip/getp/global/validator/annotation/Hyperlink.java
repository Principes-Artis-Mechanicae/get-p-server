package es.princip.getp.global.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

@Pattern(
    regexp = "^(https|ftp|mailto|tel)://.*")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = {})
public @interface Hyperlink {
    String message() default "{validation.constraints.Hyperlink.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}