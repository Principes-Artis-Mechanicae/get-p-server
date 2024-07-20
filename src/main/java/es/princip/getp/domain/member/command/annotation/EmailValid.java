package es.princip.getp.domain.member.command.annotation;

import es.princip.getp.domain.member.command.domain.model.Email;
import jakarta.validation.constraints.Pattern;

@Pattern(
    regexp = Email.EMAIL_REGEX,
    message = "{validation.constraints.Email.message}"
)
public @interface EmailValid {
}
