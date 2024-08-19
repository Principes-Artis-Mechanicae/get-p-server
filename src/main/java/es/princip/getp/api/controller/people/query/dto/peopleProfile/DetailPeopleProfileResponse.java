package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.common.dto.HashtagsResponse;
import es.princip.getp.common.dto.TechStacksResponse;
import es.princip.getp.domain.people.command.domain.Education;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    Education education,
    TechStacksResponse techStacks,
    HashtagsResponse hashtags,
    PortfoliosResponse portfolios
) {

    public static DetailPeopleProfileResponse from(final PeopleProfile profile) {
        return new DetailPeopleProfileResponse(
            profile.getIntroduction(),
            profile.getActivityArea(),
            profile.getEducation(),
            TechStacksResponse.from(profile.getTechStacks()),
            HashtagsResponse.from(profile.getHashtags()),
            PortfoliosResponse.from(profile.getPortfolios())
        );
    }
}