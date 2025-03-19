package es.princip.getp.api.controller.project.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ConfirmProjectResponseDescription {
    public static FieldDescriptor[] confirmProjectResponseDescription() {
        return new FieldDescriptor[] {
                statusField(),
                fieldWithPath("data.assignmentId").description("할당 ID"),
        };
    }
}
