package es.princip.getp.domain.project.command.presentation.description;

import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class ApplyProjectMeetingRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = ApplyProjectMeetingRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("projectName", "프로젝트 이름", clazz),
            getDescriptor("meetingLocation", "미팅 장소", clazz),
            getDescriptor("meetingSchedule[]", "미팅 일정", clazz),
            getDescriptor("contactPhoneNumber", "연락처", clazz),
            getDescriptor("description", "내용", clazz)
        };
    }
}
