package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import es.princip.getp.domain.people.dto.PortfolioForm;
import jakarta.validation.constraints.NotNull;

public record DetailPeopleProfileResponse(@NotNull String introduction,
                                          @NotNull String activityArea,
                                          @NotNull List<String> techStacks,
                                          @NotNull String education,
                                          @NotNull List<String> hashtags,
                                          @NotNull Integer completedProjectsCount,
                                          @NotNull Integer interestsCount,
                                          @NotNull List<PortfolioForm> portfolios) {

    public static DetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new DetailPeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0, 0,
            peopleProfile.getPortfolios().stream().map(portfolio -> PortfolioForm.from(portfolio.getPortfolio())).toList()
        );
    }
}