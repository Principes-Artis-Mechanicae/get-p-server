package es.princip.getp.domain.member.command.annotation;

import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.constraints.Pattern;

@Pattern(
    regexp = PhoneNumber.PHONE_REGEX,
    message = "{validation.constraints.PhoneNumber.message}"
)
public @interface PhoneNumberValid {
}