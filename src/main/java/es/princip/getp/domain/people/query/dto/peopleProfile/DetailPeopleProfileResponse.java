package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.command.domain.Education;
import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.project.domain.TechStack;

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
}