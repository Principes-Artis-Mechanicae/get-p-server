package es.princip.getp.domain.common.annotation;

import es.princip.getp.domain.common.domain.URL;
import jakarta.validation.constraints.Pattern;

@Pattern(
    regexp = URL.URL_REGEX,
    message = "{validation.constraints.URL.message}"
)
public @interface URLValid {
}
