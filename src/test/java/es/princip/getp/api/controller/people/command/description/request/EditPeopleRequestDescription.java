package es.princip.getp.api.controller.people.command.description.request;

import es.princip.getp.api.controller.people.command.dto.request.EditPeopleRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class EditPeopleRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = EditPeopleRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("nickname", "닉네임", clazz),
            getDescriptor("email", "이메일(기본값은 회원 가입 시 기입한 이메일)", clazz),
            getDescriptor("phoneNumber", "전화번호", clazz),
        };
    }
}
