package es.princip.getp.domain.people.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PeopleId extends BaseModel {

    @NotNull private final Long value;

    public PeopleId(final Long value) {
        this.value = value;
    }
}
