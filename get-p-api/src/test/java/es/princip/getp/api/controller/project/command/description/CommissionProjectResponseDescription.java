package es.princip.getp.api.controller.project.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class CommissionProjectResponseDescription {

    public static FieldDescriptor[] commissionProjectResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.projectId").description("프로젝트 ID")
        };
    }
}
