package es.princip.getp.domain.member.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class MemberId {

    private final Long value;

    public MemberId(final Long value) {
        this.value = value;
    }
}
