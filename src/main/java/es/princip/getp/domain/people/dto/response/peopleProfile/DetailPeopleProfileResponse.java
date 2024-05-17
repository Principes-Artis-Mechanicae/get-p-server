package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.values.Education;
import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.global.domain.values.Hashtag;
import es.princip.getp.global.domain.values.TechStack;
import lombok.Builder;

import java.util.List;

@Builder
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
        return DetailPeopleProfileResponse.builder()
            .introduction(peopleProfile.getIntroduction())
            .activityArea(peopleProfile.getActivityArea())
            .techStacks(peopleProfile.getTechStacks())
            .education(peopleProfile.getEducation())
            .hashtags(peopleProfile.getHashtags())
            .completedProjectsCount(0) //TODO: 완수한 프로젝트 수 계산
            .interestsCount(0) //TODO: 받은 관심 수 계산
            .portfolios(peopleProfile.getPortfolios())
            .build();
    }
}