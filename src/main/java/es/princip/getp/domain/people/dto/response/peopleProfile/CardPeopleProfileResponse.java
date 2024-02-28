package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardPeopleProfileResponse {
    @NotNull
    private final String activityArea;
    @NotNull
    private final List<String> hashtags;
    @NotNull
    private final Integer completedProjectsCount;
    @NotNull
    private final Integer interestsCount;

    public CardPeopleProfileResponse(final String activityArea, final List<Hashtag> hashtags) {
        this.activityArea = activityArea;
        this.hashtags = hashtags.stream().map(Hashtag::getValue).toList();
        //저장 프로퍼티 구현
        this.completedProjectsCount = 0;
        this.interestsCount = 0;
    }
}
