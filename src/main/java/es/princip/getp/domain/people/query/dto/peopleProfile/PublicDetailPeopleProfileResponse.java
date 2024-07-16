package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;

import java.util.List;

public record PublicDetailPeopleProfileResponse(
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {
}