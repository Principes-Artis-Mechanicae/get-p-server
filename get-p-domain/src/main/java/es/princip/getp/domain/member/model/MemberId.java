package es.princip.getp.domain.member.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MemberId extends BaseModel {

    @NotNull private final Long value;

    public MemberId(final Long value) {
        this.value = value;
    }
}
