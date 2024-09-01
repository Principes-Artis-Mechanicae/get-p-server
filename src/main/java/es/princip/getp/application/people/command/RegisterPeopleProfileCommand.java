package es.princip.getp.application.people.command;

import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.common.model.TechStack;
import es.princip.getp.domain.people.model.Education;
import es.princip.getp.domain.people.model.Portfolio;

import java.util.List;

public record RegisterPeopleProfileCommand(
    Long memberId,
    String introduction,
    String activityArea,
    Education education,
    List<Hashtag> hashtags,
    List<TechStack> techStacks,
    List<Portfolio> portfolios
) {
}