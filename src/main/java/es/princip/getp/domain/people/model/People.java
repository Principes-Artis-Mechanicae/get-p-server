package es.princip.getp.domain.people.model;

import es.princip.getp.domain.BaseEntity;
import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.people.exception.AlreadyRegisteredPeopleProfileException;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class People extends BaseEntity implements Likeable, LikeReceivable {

    private Long peopleId;
    @NotNull private Long memberId;
    private PeopleInfo info;
    private PeopleProfile profile;

    public People(
        final Long peopleId,
        final Long memberId,
        final PeopleInfo info,
        final PeopleProfile profile,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.peopleId = peopleId;
        this.memberId = memberId;
        this.info = info;
        this.profile = profile;

        validate();
    }

    public People(final Long memberId, final PeopleInfo info) {
        this.memberId = memberId;
        this.info = info;

        validate();
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
