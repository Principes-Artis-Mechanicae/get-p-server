package es.princip.getp.domain.project.dto.request;

import es.princip.getp.domain.project.domain.EstimatedDuration;
import es.princip.getp.domain.project.domain.MeetingType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateProjectRequest(
    @NotNull String title,
    @NotNull Long payment,
    @NotNull LocalDate applicationDeadline,
    @NotNull EstimatedDuration estimatedDuration,
    @NotNull String description,
    @NotNull MeetingType meetingType,
    @NotNull List<String> attachmentUris,
    @NotNull List<String> hashtags
) {
}