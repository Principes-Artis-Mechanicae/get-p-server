package es.princip.getp.api.controller.project.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ScheduleMeetingResponseDescription {

    public static FieldDescriptor[] scheduleMeetingResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.meetingId").description("미팅 ID"),
        };
    }
}
