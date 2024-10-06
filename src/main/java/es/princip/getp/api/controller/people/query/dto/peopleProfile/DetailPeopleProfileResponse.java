package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.domain.people.model.Education;

import java.util.List;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    Education education,
    List<String> techStacks,
    List<String> hashtags,
    List<PortfolioResponse> portfolios
) {
}