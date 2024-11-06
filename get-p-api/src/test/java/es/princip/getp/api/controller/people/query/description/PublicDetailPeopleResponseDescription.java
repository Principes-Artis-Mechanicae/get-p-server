package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


public class PublicDetailPeopleResponseDescription {

    public static FieldDescriptor[] publicDetailPeopleResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.peopleId").description("피플 ID"),
            fieldWithPath("data.nickname").description("닉네임"),
            fieldWithPath("data.profileImageUri").description("프로필 이미지 URI"),
            fieldWithPath("data.completedProjectsCount").description("완수한 프로젝트 수"),
            fieldWithPath("data.likesCount").description("받은 좋아요 수"),
            fieldWithPath("data.profile.hashtags[]").description("해시태그")
        };
    }
}