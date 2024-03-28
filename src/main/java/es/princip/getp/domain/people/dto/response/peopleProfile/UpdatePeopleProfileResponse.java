package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import es.princip.getp.domain.people.dto.PortfolioForm;
import java.util.List;

public record UpdatePeopleProfileResponse(
    String introduction,
    String activityArea,
    List<String> techStacks,
    String education,
    List<String> hashtags,
    List<PortfolioForm> portfolios
) {

    public static UpdatePeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new UpdatePeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            peopleProfile.getPortfolios().stream()
                .map(portfolio -> PortfolioForm.from(portfolio.getPortfolio())).toList()
        );
    }
}