package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.apply.model.ProjectApplicationType;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record ProjectApplicationDetailResponse(
    Long applicationId,
    ProjectApplicationType type,
    ProjectDetailResponse project,
    Duration expectedDuration,
    ProjectApplicationStatus status,
    String description,
    List<String> attachmentFiles,
    @JsonInclude(NON_NULL) List<ProjectApplicationDetailTeammateResponse> teammates
) {
}