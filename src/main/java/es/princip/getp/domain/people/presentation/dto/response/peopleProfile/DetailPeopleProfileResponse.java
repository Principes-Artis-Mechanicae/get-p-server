package es.princip.getp.domain.people.presentation.dto.response.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.domain.Education;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.people.domain.Portfolio;
import es.princip.getp.domain.project.domain.TechStack;
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

    public static DetailPeopleProfileResponse from(final PeopleProfile profile) {
        return DetailPeopleProfileResponse.builder()
            .introduction(profile.getIntroduction())
            .activityArea(profile.getActivityArea())
            .techStacks(profile.getTechStacks())
            .education(profile.getEducation())
            .hashtags(profile.getHashtags())
            .completedProjectsCount(0) //TODO: 완수한 프로젝트 수 계산
            .interestsCount(0) //TODO: 받은 관심 수 계산
            .portfolios(profile.getPortfolios())
            .build();
    }
}