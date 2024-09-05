package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.common.model.PhoneNumberPattern;
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