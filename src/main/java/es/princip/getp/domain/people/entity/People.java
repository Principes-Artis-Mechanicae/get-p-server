package es.princip.getp.domain.people.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private String profileImageUri;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Member member;

    @OneToOne(mappedBy = "people")
    private PeopleProfile peopleProfile;

    @Builder
    public People(final String nickname,
        final String email,
        final String phoneNumber,
        final PeopleType peopleType,
        final String profileImageUri,
        final String accountNumber,
        final Member member
    ) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.peopleType = peopleType;
        this.profileImageUri = profileImageUri;
        this.accountNumber = accountNumber;
        this.member = member;
    }

    public void update(UpdatePeopleRequest request) {
        this.nickname = request.nickname();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
        this.peopleType = PeopleType.valueOf(request.peopleType());
        this.profileImageUri = request.profileImageUri();
        this.accountNumber = request.accountNumber();
    }
}
