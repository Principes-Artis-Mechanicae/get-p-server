package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.values.Education;
import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.global.domain.values.Hashtag;
import es.princip.getp.global.domain.values.TechStack;
import java.util.List;

public record UpdatePeopleProfileResponse(
    String introduction,
    String activityArea,
    List<TechStack> techStacks,
    Education education,
    List<Hashtag> hashtags,
    List<Portfolio> portfolios
) {

    public static UpdatePeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new UpdatePeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags(),
            peopleProfile.getPortfolios()
        );
    }
}