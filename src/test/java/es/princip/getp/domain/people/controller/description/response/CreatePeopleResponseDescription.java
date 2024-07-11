package es.princip.getp.domain.people.controller.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.support.FieldDescriptorHelper.getDescriptor;

public class CreatePeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID")
        };
    }
}