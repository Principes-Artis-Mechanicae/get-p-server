package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import jakarta.validation.constraints.NotNull;

public record PublicDetailPeopleProfileResponse(
                                          @NotNull List<String> hashtags,
                                          @NotNull Integer completedProjectsCount,
                                          @NotNull Integer interestsCount) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PublicDetailPeopleProfileResponse(
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0, 0
        );
    }
}