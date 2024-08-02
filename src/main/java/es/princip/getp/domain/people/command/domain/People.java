package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.command.domain.model.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Embedded
    @NotNull
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    @NotNull
    private PeopleType peopleType;

    @Column(name = "member_id")
    @NotNull
    private Long memberId;

    @Builder
    public People(
        final Email email,
        final PeopleType peopleType,
        final Long memberId
    ) {
        this.email = email;
        this.peopleType = peopleType;
        this.memberId = memberId;
    }

    private void setEmail(final Email email) {
        Objects.requireNonNull(email);
        this.email = email;
    }

    private void setPeopleType(final PeopleType peopleType) {
        Objects.requireNonNull(peopleType);
        this.peopleType = peopleType;
    }

    /**
     * 피플 정보를 수정한다. 이때, 수정하지 않은 정보도 함께 전달해야 한다.
     *
     * @param email 이메일
     * @param peopleType 피플 유형
     */
    public void edit(
        final Email email,
        final PeopleType peopleType
    ) {
        setEmail(email);
        setPeopleType(peopleType);
    }
}
