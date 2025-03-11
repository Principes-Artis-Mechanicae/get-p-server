package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class MyPeopleResponseDescription {

    public static FieldDescriptor[] myPeopleResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.peopleId").description("피플 ID"),
            fieldWithPath("data.email").description("이메일"),
            fieldWithPath("data.nickname").description("닉네임"),
            fieldWithPath("data.phoneNumber").description("전화번호"),
            fieldWithPath("data.profileImageUri").description("프로필 이미지 URI"),
            fieldWithPath("data.completedProjectsCount").description("완수한 프로젝트 수"),
            fieldWithPath("data.likesCount").description("받은 좋아요 수"),
            fieldWithPath("data.createdAt").description("피플 정보 등록 일시"),
            fieldWithPath("data.updatedAt").description("최근 피플 정보 수정 일시")
        };
    }
}