package es.princip.getp.api.controller.client.command.description;

import es.princip.getp.api.controller.client.command.dto.request.RegisterMyClientRequest;
import es.princip.getp.domain.client.model.Address;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class RegisterMyClientRequestDescription {

    public static FieldDescriptor[] registerMyClientRequestDescription() {
        final Class<?> clazz = RegisterMyClientRequest.class;
        return new FieldDescriptor[]{
            fieldWithConstraint("nickname", clazz).description("닉네임"),
            fieldWithConstraint("email", clazz)
                .optional()
                .description("이메일. 미입력 시 회원 정보의 이메일로 등록됩니다."),
            fieldWithConstraint("phoneNumber", clazz).description("전화번호"),
            fieldWithPath("address").optional().description("주소"),
            fieldWithConstraint("address.zipcode", Address.class).description("우편번호"),
            fieldWithConstraint("address.street", Address.class).description("도로명 주소"),
            fieldWithConstraint("address.detail", Address.class)
                .optional()
                .description("상세 주소")
        };
    }
}
