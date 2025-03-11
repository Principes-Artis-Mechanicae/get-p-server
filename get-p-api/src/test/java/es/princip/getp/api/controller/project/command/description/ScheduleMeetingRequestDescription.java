package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;

public class ScheduleMeetingRequestDescription {

    public static FieldDescriptor[] scheduleMeetingRequestDescription() {
        final Class<?> clazz = ScheduleMeetingRequest.class;
        return new FieldDescriptor[] {
            fieldWithConstraint("applicantId", clazz).description("지원자 ID"),
            fieldWithConstraint("location", clazz).description("미팅 장소"),
            fieldWithConstraint("schedule.date", clazz).description("미팅 날짜"),
            fieldWithConstraint("schedule.startTime", clazz).description("미팅 시작 시간"),
            fieldWithConstraint("schedule.endTime", clazz).description("미팅 종료 시간"),
            fieldWithConstraint("phoneNumber", clazz).description("연락처"),
            fieldWithConstraint("description", clazz).description("요구사항"),
        };
    }
}
