package es.princip.getp.domain.people.presentation.dto.response.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.domain.Education;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.project.domain.TechStack;

import java.util.List;

public record PeopleProfileResponse(
    String introduction,
    String activityArea,
    List<TechStack> techStacks,
    Education education,
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static PeopleProfileResponse from(final PeopleProfile profile) {
        return new PeopleProfileResponse(
            profile.getIntroduction(),
            profile.getActivityArea(),
            profile.getTechStacks(),
            profile.getEducation(),
            profile.getHashtags(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}