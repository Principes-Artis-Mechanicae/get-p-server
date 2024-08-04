package es.princip.getp.domain.project.command.presentation.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class ScheduleMeetingResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("meetingId","미팅 ID")
        };
    }
}
