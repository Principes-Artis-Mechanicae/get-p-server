package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;

import java.util.List;

public record PeopleProfileData(
    String introduction,
    String activityArea,
    Education education,
    List<Hashtag> hashtags,
    List<TechStack> techStacks,
    List<Portfolio> portfolios
) {

}
