package es.princip.getp.api.controller.project.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ApplyProjectResponseDescription {

    public static FieldDescriptor[] applyProjectResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.applicationId").description("프로젝트 지원 ID"),
        };
    }
}
