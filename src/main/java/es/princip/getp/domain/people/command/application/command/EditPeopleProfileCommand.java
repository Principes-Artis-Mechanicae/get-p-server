package es.princip.getp.domain.people.command.application.command;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import es.princip.getp.domain.people.command.domain.Education;
import es.princip.getp.domain.people.command.domain.Portfolio;

import java.util.List;

public record EditPeopleProfileCommand(
    Long memberId,
    String introduction,
    String activityArea,
    Education education,
    List<Hashtag> hashtags,
    List<TechStack> techStacks,
    List<Portfolio> portfolios
) {
}
