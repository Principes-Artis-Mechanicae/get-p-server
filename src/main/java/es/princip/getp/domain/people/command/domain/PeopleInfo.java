package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.member.command.domain.model.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleInfo {

    @NotNull
    @Embedded
    private Email email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    public PeopleInfo(final Email email, final PeopleType peopleType) {
        this.email = email;
        this.peopleType = peopleType;
    }
}
