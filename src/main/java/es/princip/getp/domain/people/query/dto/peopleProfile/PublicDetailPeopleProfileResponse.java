package es.princip.getp.domain.people.query.dto.peopleProfile;

import java.util.List;

public record PublicDetailPeopleProfileResponse(
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {
}