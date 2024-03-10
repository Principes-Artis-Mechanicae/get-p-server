package es.princip.getp.domain.project.dto.request;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.enums.MeetingType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CreateProjectRequest(
    @NotNull String title,
    @NotNull Long payment,
    @NotNull LocalDate applicationDeadline,
    @NotNull LocalDate estimatedStartDate,
    @NotNull LocalDate estimatedEndDate,
    @NotNull String description,
    @NotNull MeetingType meetingType,
    @NotNull List<String> attachmentUris,
    @NotNull List<String> hashtags
) {

    public Project toEntity(final Client client) {
        return Project.builder()
            .title(title)
            .payment(payment)
            .applicationDeadline(applicationDeadline)
            .estimatedStartDate(estimatedStartDate)
            .estimatedEndDate(estimatedEndDate)
            .description(description)
            .meetingType(meetingType)
            .attachmentUris(attachmentUris)
            .hashtags(hashtags)
            .client(client)
            .build();
    }
}