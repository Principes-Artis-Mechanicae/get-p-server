package es.princip.getp.application.project.apply.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.domain.people.model.Education;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record ProjectApplicantResponse(
    Long peopleId,
    String nickname,
    String profileImageUrl,
    Education education,
    ProjectApplicationStatus status,
    @JsonInclude(NON_NULL) List<ProjectApplicantTeammateResponse> teammates
) {
}