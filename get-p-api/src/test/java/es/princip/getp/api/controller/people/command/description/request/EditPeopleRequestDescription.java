package es.princip.getp.api.controller.people.command.description.request;

import es.princip.getp.api.controller.people.command.dto.request.EditPeopleRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;

public class EditPeopleRequestDescription {

    public static FieldDescriptor[] editPeopleRequestDescription() {
        final Class<?> clazz = EditPeopleRequest.class;
        return new FieldDescriptor[] {
            fieldWithConstraint("nickname", clazz).description("닉네임"),
            fieldWithConstraint("email", clazz).description("이메일(기본값은 회원 가입 시 기입한 이메일)"),
            fieldWithConstraint("phoneNumber", clazz).description("전화번호")
        };
    }
}
