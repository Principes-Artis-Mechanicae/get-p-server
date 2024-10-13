package es.princip.getp.api.controller.people.query.dto.people;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import lombok.Getter;

@Getter
public class PeopleDetailResponse {
    private final Long peopleId;
    private final String nickname;
    private final String profileImageUri;
    private final long completedProjectsCount;
    private final long likesCount;
    @JsonInclude(NON_NULL) private final Boolean liked;
    private PeopleProfileDetailResponse profile;

    public PeopleDetailResponse(
        final Long peopleId,
        final String nickname,
        final String profileImageUri,
        final long completedProjectsCount,
        final long likesCount,
        final Boolean liked,
        final PeopleProfileDetailResponse profile
    ) {
        this.peopleId = peopleId;
        this.nickname = nickname;
        this.profileImageUri = profileImageUri;
        this.completedProjectsCount = completedProjectsCount;
        this.likesCount = likesCount;
        this.liked = liked;
        this.profile = profile;
    }

    public PeopleDetailResponse mosaic(final PeopleProfileDetailResponse profile) {
        this.profile = profile;
        return this;
    }
}
