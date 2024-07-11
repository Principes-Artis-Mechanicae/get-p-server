package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "people")
    private PeopleProfile profile;

    @Builder
    public People(
        final String email,
        final PeopleType peopleType,
        final Member member
    ) {
        this.email = email;
        this.peopleType = peopleType;
        this.member = member;
    }

    public static People from(final Member member, final CreatePeopleRequest request) {
        return People.builder()
            .email(request.email())
            .peopleType(request.peopleType())
            .member(member)
            .build();
    }

    public String getEmail() {
        return this.email == null ? this.member.getEmail() : this.email;
    }

    public String getNickname() {
        return this.member.getNickname();
    }

    public String getPhoneNumber() {
        return this.member.getPhoneNumber();
    }

    public String getProfileImageUri() {
        return this.member.getProfileImageUri();
    }

    public void update(final UpdatePeopleRequest request) {
        this.email = request.email();
        this.peopleType = request.peopleType();
    }
}
