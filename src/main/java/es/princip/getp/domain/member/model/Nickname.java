package es.princip.getp.domain.member.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Nickname extends BaseModel {

    @NotNull private final String value;

    public Nickname(final String value) {
        this.value = value;

        validate();
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }
}
