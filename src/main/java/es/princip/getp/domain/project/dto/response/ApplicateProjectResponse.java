package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.project.domain.ProjectApplication;
import es.princip.getp.domain.project.domain.ProjectApplicationAttachmentFile;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ApplicateProjectResponse(
    @NotNull Long projectApplicationId,
    @NotNull CardProjectResponse project,
    @NotNull LocalDate expectedStartDate,
    @NotNull LocalDate expectedEndDate,
    @NotNull String description,
    @NotNull List<String> attachmentUris
) {

    public static ApplicateProjectResponse from(final ProjectApplication projectApplication) {
        return new ApplicateProjectResponse(
            projectApplication.getId(),
            CardProjectResponse.from(projectApplication.getProject()),
            projectApplication.getExpectedDuration().getStartDate(),
            projectApplication.getExpectedDuration().getEndDate(),
            projectApplication.getDescription(),
            projectApplication.getAttachmentFiles().stream().map(ProjectApplicationAttachmentFile::getUri).toList()
        );
    }
}
