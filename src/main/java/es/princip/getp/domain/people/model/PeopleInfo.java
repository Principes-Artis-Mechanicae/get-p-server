package es.princip.getp.domain.people.model;

import es.princip.getp.domain.BaseModel;
import es.princip.getp.domain.member.model.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PeopleInfo extends BaseModel {

    @NotNull private Email email;
    @NotNull private PeopleType peopleType;

    public PeopleInfo(final Email email, final PeopleType peopleType) {
        this.email = email;
        this.peopleType = peopleType;

        validate();
    }
}
