package es.princip.getp.domain.member.command.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Column(name = "nickname")
    @NotNull
    private String value;

    private Nickname(final String value) {
        this.value = value;
    }

    public static Nickname of(final String value) {
        validate(value);
        return new Nickname(value);
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value, "닉네임은 필수 입력 값입니다.");
    }
}
