package es.princip.getp.domain.people.command.domain;

import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.common.domain.TechStack;

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
