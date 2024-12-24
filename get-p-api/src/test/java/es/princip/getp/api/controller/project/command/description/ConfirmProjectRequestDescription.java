package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.AssignmentPeopleRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;

public class ConfirmProjectRequestDescription {

    public static FieldDescriptor[] confirmProjectRequestDescription() {
        final Class<?> clazz = AssignmentPeopleRequest.class;
        return new FieldDescriptor[] {
                fieldWithConstraint("applicantId", clazz).description("지원자 ID")
        };
    }
}
