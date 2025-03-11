package es.princip.getp.api.controller.client.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ClientResponseDescription {

    public static FieldDescriptor[] clientResponseDescription() {
        return new FieldDescriptor[]{
            statusField(),
            fieldWithPath("data.clientId").description("의뢰자 ID"),
            fieldWithPath("data.email").description("이메일"),
            fieldWithPath("data.nickname").description("닉네임"),
            fieldWithPath("data.phoneNumber").description("전화번호"),
            fieldWithPath("data.profileImageUri").description("프로필 이미지 URI"),
            fieldWithPath("data.createdAt").description("의뢰자 정보 등록 일시"),
            fieldWithPath("data.updatedAt").description("최근 의뢰자 정보 수정 일시"),
            fieldWithPath("data.address.zipcode").description("우편번호"),
            fieldWithPath("data.address.street").description("도로명 주소"),
            fieldWithPath("data.address.detail").description("상세 주소")
        };
    }
}
