package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.model.Education;

import java.util.List;

public record DetailPeopleProfileResponse(
    String introduction,
    String activityArea,
    Education education,
    List<String> techStacks,
    HashtagsResponse hashtags,
    List<PortfolioResponse> portfolios
) {
}