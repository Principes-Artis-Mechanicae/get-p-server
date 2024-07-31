package es.princip.getp.domain.project.command.presentation.dto.request;

import java.util.List;

import es.princip.getp.domain.project.command.domain.MeetingSchedule;
import jakarta.validation.constraints.NotNull;

public record ApplyProjectMeetingRequest(
    @NotNull Long peopleId,
    @NotNull Long applicationId,
    @NotNull String projectName,
    @NotNull String meetingLocation,
    @NotNull List<MeetingSchedule> meetingSchedule,
    @NotNull String contactPhoneNumber,
    @NotNull String description
) {
}