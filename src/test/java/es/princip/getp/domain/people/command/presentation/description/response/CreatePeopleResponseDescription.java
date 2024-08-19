package es.princip.getp.domain.people.command.presentation.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.docs.FieldDescriptorHelper.getDescriptor;

public class CreatePeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID")
        };
    }
}