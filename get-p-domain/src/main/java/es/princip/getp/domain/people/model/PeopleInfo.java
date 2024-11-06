package es.princip.getp.domain.people.model;

import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PeopleInfo extends BaseModel {

    @NotNull private Email email;

    public PeopleInfo(final Email email) {
        this.email = email;

        validate();
    }
}
