package es.princip.getp.api.controller.people.command.description.request;

import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;

public class RegisterPeopleRequestDescription {

    public static FieldDescriptor[] registerPeopleRequestDescription() {
        final Class<?> clazz = RegisterPeopleRequest.class;
        return new FieldDescriptor[] {
            fieldWithConstraint("nickname", clazz).description("닉네임"),
            fieldWithConstraint("email", clazz)
                .optional()
                .description("이메일. 미입력 시 회원 정보의 이메일로 등록됩니다."),
            fieldWithConstraint("phoneNumber", clazz).description("전화번호")
        };
    }
}
