package es.princip.getp.domain.people.model;

import es.princip.getp.domain.BaseEntity;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.people.exception.AlreadyRegisteredPeopleProfileException;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class People extends BaseEntity {

    private Long id;
    @NotNull private Long memberId;
    private PeopleInfo info;
    private PeopleProfile profile;

    public People(
        final Long id,
        final Long memberId,
        final PeopleInfo info,
        final PeopleProfile profile,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.memberId = memberId;
        this.info = info;
        this.profile = profile;

        validate();
    }

    public static People of(final Long memberId, final PeopleInfo info) {
        return new People(null, memberId, info, null, null, null);
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
}