package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.exception.AlreadyRegisteredPeopleProfileException;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity implements Likeable, LikeReceivable {

    @Id
    @Column(name = "people_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peopleId;

    @NotNull
    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    private PeopleInfo info;

    @Embedded
    private PeopleProfile profile;

    public People(final Long memberId, final PeopleInfo info) {
        this.memberId = memberId;
        this.info = info;
    }

    public void editInfo(final Email email, final PeopleType peopleType) {
        this.info = new PeopleInfo(email, peopleType);
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
        this.profile = buildProfile(data);
    }

    public boolean isProfileRegistered() {
        return this.profile != null && this.profile.isRegistered();
    }

    public void editProfile(final PeopleProfileData data) {
        if (!isProfileRegistered()) {
            throw new NotRegisteredPeopleProfileException();
        }
        this.profile = buildProfile(data);
    }

    @Override
    public Long getId() {
        return this.peopleId;
    }
}
