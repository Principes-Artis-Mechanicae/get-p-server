package es.princip.getp.domain.common.model;

import es.princip.getp.domain.BaseModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class URL extends BaseModel {

    public static final String URL_REGEX = "^(https|ftp|mailto|tel)://.*";

    @NotNull
    @Pattern(regexp = URL_REGEX)
    private final String value;

    public URL(final String value) {
        this.value = value;
        validate();
    }

    public static URL from(final String url) {
        return new URL(url);
    }
}
