package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.dto.TechStacksResponse;
import es.princip.getp.domain.people.command.domain.Education;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    Education education,
    Integer completedProjectsCount,
    Integer interestsCount,
    TechStacksResponse techStacks,
    HashtagsResponse hashtags,
    PortfoliosResponse portfolios
) {

    public static DetailPeopleProfileResponse from(final PeopleProfile profile) {
        return new DetailPeopleProfileResponse(
            profile.getIntroduction(),
            profile.getActivityArea(),
            profile.getEducation(),
            0,
            0,
            TechStacksResponse.from(profile.getTechStacks()),
            HashtagsResponse.from(profile.getHashtags()),
            PortfoliosResponse.from(profile.getPortfolios())
        );
    }
}