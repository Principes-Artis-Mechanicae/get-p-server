package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class TeammateId extends BaseModel {

    @NotNull private final Long value;

    public TeammateId(final Long value) {
        this.value = value;
    }
}
