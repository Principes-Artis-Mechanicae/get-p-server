package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.hashtag.domain.Hashtag;
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

    public static PeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}