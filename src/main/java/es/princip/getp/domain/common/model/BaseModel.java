package es.princip.getp.domain.common.model;

import es.princip.getp.domain.common.exception.NotValidDomainModelException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.stream.Collectors;

public abstract class BaseModel {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    protected void validate() {
        final String message = validator.validate(this).stream()
            .map(violation -> violation.getPropertyPath() + "은(는) " + violation.getMessage())
            .collect(Collectors.joining(", "));
        if (!message.isEmpty()) {
            throw new NotValidDomainModelException(message);
        }
    }
}
