package es.princip.getp.domain.project.command.presentation.description;

import es.princip.getp.domain.project.command.presentation.dto.request.ScheduleMeetingRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class ScheduleMeetingRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = ScheduleMeetingRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("projectName", "프로젝트 이름", clazz),
            getDescriptor("location", "미팅 장소", clazz),
            getDescriptor("schedule", "미팅 일정", clazz),
            getDescriptor("phoneNumber", "연락처", clazz),
            getDescriptor("description", "내용", clazz)
        };
    }
}
