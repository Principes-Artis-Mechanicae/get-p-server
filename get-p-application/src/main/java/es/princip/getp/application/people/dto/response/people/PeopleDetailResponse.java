package es.princip.getp.application.people.dto.response.people;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PortfolioResponse;
import es.princip.getp.domain.people.model.Education;
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

    public PeopleDetailResponse mosaic(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<String> techStacks,
        final List<PortfolioResponse> portfolios
    ) {
        if (profile != null) {
            profile.mosaic(introduction, activityArea, education, techStacks, portfolios);
        }
        return this;
    }
}
