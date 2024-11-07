package es.princip.getp.application.people.dto.response.peopleProfile;

import java.util.List;

import es.princip.getp.domain.people.model.Education;
import lombok.Getter;

@Getter
public class PeopleProfileDetailResponse {
    private String introduction;
    private String activityArea;
    private Education education;
    private List<String> techStacks;
    private final List<String> hashtags;
    private List<PortfolioResponse> portfolios;

    public PeopleProfileDetailResponse(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<String> techStacks,
        final List<String> hashtags,
        final List<PortfolioResponse> portfolios
    ) {
        this.introduction = introduction;
        this.activityArea = activityArea;
        this.education = education;
        this.techStacks = techStacks;
        this.hashtags = hashtags;
        this.portfolios = portfolios;
    }

    public PeopleProfileDetailResponse mosaic(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<String> techStacks,
        final List<PortfolioResponse> portfolios
    ) {
        this.introduction = introduction;
        this.activityArea = activityArea;
        this.education = education;
        this.techStacks = techStacks;
        this.portfolios = portfolios;
        return this;
    }
}
