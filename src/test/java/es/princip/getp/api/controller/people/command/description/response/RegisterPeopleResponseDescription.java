package es.princip.getp.api.controller.people.command.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class RegisterPeopleResponseDescription {

    public static FieldDescriptor[] registerPeopleResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.peopleId").description("피플 ID")
        };
    }
}