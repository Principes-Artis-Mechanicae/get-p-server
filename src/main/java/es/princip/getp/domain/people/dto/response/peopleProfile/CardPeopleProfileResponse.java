package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import jakarta.validation.constraints.NotNull;

public record CardPeopleProfileResponse(@NotNull String activityArea,
                                        @NotNull List<String> hashtags,
                                        @NotNull Integer completedProjectsCount,
                                        @NotNull Integer interestsCount) {

    public static CardPeopleProfileResponse from(final String activityArea, final List<PeopleHashtag> hashtags) {
        return new CardPeopleProfileResponse(
            activityArea,
            hashtags.stream().map(PeopleHashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0, 0
        );
    }
}
