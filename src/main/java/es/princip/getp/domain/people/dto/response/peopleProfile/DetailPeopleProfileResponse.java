package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.values.Education;
import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.global.domain.values.Hashtag;
import es.princip.getp.global.domain.values.TechStack;
import java.util.List;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    List<TechStack> techStacks,
    Education education,
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount,
    List<Portfolio> portfolios
) {

    public static DetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new DetailPeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags(),
            //TODO: 계산 프로퍼티 구현
            0,
            0,
            peopleProfile.getPortfolios()
        );
    }
}