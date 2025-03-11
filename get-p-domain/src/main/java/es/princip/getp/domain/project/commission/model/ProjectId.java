package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ProjectId extends BaseModel {

    @NotNull private final Long value;

    public ProjectId(final Long value) {
        this.value = value;
    }
}
