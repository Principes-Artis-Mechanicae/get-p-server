package es.princip.getp.domain.project.command.presentation.dto.request;

import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.annotation.PhoneNumberPattern;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ScheduleMeetingRequest(
    @NotNull Long applicantId,
    @NotNull String location,
    @NotNull @Valid MeetingSchedule schedule,
    @NotNull @PhoneNumberPattern String phoneNumber,
    @NotNull String description
) {
}