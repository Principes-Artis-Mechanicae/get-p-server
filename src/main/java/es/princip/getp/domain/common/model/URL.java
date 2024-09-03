package es.princip.getp.domain.common.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class URL extends BaseModel {

    public static final String URL_REGEX = "^(https|ftp|mailto|tel)://.*";

    @NotNull
    @URLPattern
    private final String value;

    public URL(final String value) {
        this.value = value;
        validate();
    }

    public static URL from(final String url) {
        return new URL(url);
    }
}
