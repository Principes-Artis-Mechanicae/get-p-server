package es.princip.getp.domain.project.command.presentation.description;

import es.princip.getp.domain.project.command.presentation.dto.request.ScheduleMeetingRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.docs.FieldDescriptorHelper.getDescriptor;

public class ScheduleMeetingRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = ScheduleMeetingRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("applicantId", "지원자 ID", clazz),
            getDescriptor("location", "미팅 장소", clazz),
            getDescriptor("schedule.date", "미팅 날짜", clazz),
            getDescriptor("schedule.startTime", "미팅 시작 시간", clazz),
            getDescriptor("schedule.endTime", "미팅 종료 시간", clazz),
            getDescriptor("phoneNumber", "연락처", clazz),
            getDescriptor("description", "요구사항", clazz)
        };
    }
}
