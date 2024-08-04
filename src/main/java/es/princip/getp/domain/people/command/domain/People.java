package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.exception.AlreadyRegisteredPeopleProfileException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity {

    @Id
    @Column(name = "people_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peopleId;

    @NotNull
    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    private PeopleInfo peopleInfo;

    @Embedded
    private PeopleProfile peopleProfile;

    public People(final Long memberId, final PeopleInfo peopleInfo) {
        this.memberId = memberId;
        this.peopleInfo = peopleInfo;
    }

    public void editInfo(final Email email, final PeopleType peopleType) {
        this.peopleInfo = new PeopleInfo(email, peopleType);
    }

    private PeopleProfile buildProfile(final PeopleProfileData data) {
        return PeopleProfile.builder()
            .introduction(data.introduction())
            .activityArea(data.activityArea())
            .education(data.education())
            .hashtags(data.hashtags())
            .techStacks(data.techStacks())
            .portfolios(data.portfolios())
            .build();
    }

    public void registerProfile(final PeopleProfileData data) {
        if (isProfileRegistered()) {
            throw new AlreadyRegisteredPeopleProfileException();
        }
        this.peopleProfile = buildProfile(data);
    }

    public boolean isProfileRegistered() {
        return this.peopleProfile != null;
    }

    public void editProfile(final PeopleProfileData data) {
        this.peopleProfile = buildProfile(data);
    }
}
