package es.princip.getp.domain.common.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PhoneNumber extends BaseModel {

    public static final String PHONE_REGEX = "^[0-9]+$";

    @NotNull
    @PhoneNumberPattern
    private final String value;

    public PhoneNumber(final String value) {
        this.value = value;
        validate();
    }

    public static PhoneNumber from(final String value) {
        return new PhoneNumber(value);
    }
}
