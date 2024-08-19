package es.princip.getp.domain.member.command.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class Nickname {

    private final String value;

    public Nickname(final String value) {
        this.value = value;
    }

    public static Nickname of(final String value) {
        validate(value);
        return new Nickname(value);
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value);
    }
}
