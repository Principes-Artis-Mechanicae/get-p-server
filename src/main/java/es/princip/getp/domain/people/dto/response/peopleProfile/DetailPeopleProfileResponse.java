package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import es.princip.getp.domain.people.dto.PortfolioForm;
import java.util.List;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    List<String> techStacks,
    String education,
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount,
    List<PortfolioForm> portfolios) {

    public static DetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new DetailPeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //TODO: 계산 프로퍼티 구현
            0,
            0,
            peopleProfile.getPortfolios().stream()
                .map(portfolio -> PortfolioForm.from(portfolio.getPortfolio())).toList()
        );
    }
}