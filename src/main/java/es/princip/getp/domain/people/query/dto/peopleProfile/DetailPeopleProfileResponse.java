package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.people.command.domain.Education;

import java.util.List;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    List<String> techStacks,
    Education education,
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount,
    List<PortfolioResponse> portfolios
) {
}