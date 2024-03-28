package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import java.util.List;

public record PeopleProfileResponse(
    String introduction,
    String activityArea,
    List<String> techStacks,
    String education,
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount) {

    public static PeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}