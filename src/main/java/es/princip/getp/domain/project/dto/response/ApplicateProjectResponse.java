package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.project.entity.ProjectApplication;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import es.princip.getp.domain.project.entity.AttachmentFile;

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
            projectApplication.getProjectApplicationId(),
            CardProjectResponse.from(projectApplication.getProject()),
            projectApplication.getExpectedDuration().getStartDate(),
            projectApplication.getExpectedDuration().getEndDate(),
            projectApplication.getDescription(),
            projectApplication.getAttachmentFiles().stream().map(AttachmentFile::getUri).toList()
        );
    }
}
