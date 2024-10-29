package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.Education;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.apply.model.ProjectApplicationType;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record ProjectApplicationFormResponse(
    Long applicationId,
    ProjectApplicationType type,
    ProjectDetailResponse project,
    String nickname,
    List<String> techStacks,
    String activityArea,
    Education education,
    String introduction,
    List<PortfolioResponse> portfolios,
    Duration expectedDuration,
    ProjectApplicationStatus status,
    String description,
    List<String> attachmentFiles,
    @JsonInclude(NON_NULL) List<ProjectApplicationDetailTeammateResponse> teammates
) {
}