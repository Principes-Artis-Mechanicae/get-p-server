package es.princip.getp.api.controller.project.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class RegisterProjectResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("projectId", "프로젝트 ID")
        };
    }
}
