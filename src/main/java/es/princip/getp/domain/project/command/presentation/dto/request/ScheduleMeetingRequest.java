package es.princip.getp.domain.project.command.presentation.dto.request;

import java.util.List;

import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.annotation.PhoneNumberPattern;
import jakarta.validation.constraints.NotNull;

public record ScheduleMeetingRequest(
    @NotNull Long applicantId,
    @NotNull String meetingLocation,
    @NotNull List<MeetingSchedule> meetingSchedules,
    @NotNull @PhoneNumberPattern String contactPhoneNumber,
    @NotNull String description
) {
}