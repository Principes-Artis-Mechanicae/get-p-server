package es.princip.getp.domain.project.command.presentation.dto.request;

import java.util.List;

import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.annotation.PhoneNumberPattern;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.validation.constraints.NotNull;

public record ScheduleMeetingRequest(
    @NotNull Long applicantId,
    @NotNull String meetingLocation,
    @NotNull List<MeetingSchedule> meetingSchedules,
    @PhoneNumberPattern PhoneNumber contactPhoneNumber,
    @NotNull String description
) {
}