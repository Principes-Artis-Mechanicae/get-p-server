package es.princip.getp.domain.people.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    @Column(name = "profile_image_uri")
    @Setter
    private String profileImageUri;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "people")
    private PeopleProfile peopleProfile;

    @Builder
    public People(
        final Long peopleId,
        final String nickname,
        final String email,
        final String phoneNumber,
        final PeopleType peopleType,
        final String profileImageUri,
        final Member member
    ) {
        this.peopleId = peopleId;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.peopleType = peopleType;
        this.profileImageUri = profileImageUri;
        this.member = member;
        member.setPeople(this);
    }

    public void update(final UpdatePeopleRequest request) {
        this.nickname = request.nickname();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
        this.peopleType = request.peopleType();
        this.profileImageUri = request.profileImageUri();
    }
}
