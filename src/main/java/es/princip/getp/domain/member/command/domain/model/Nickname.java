package es.princip.getp.domain.member.command.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Column(name = "nickname")
    private String value;

    private Nickname(final String value) {
        this.value = value;
    }

    public static Nickname of(final String value) {
        validate(value);
        return new Nickname(value);
    }

    private static void validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("닉네임은 필수 입력 값입니다.");
        }
    }
}
