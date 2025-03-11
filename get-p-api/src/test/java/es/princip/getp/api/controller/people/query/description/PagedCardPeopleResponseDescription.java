package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


public class PagedCardPeopleResponseDescription {

    public static FieldDescriptor[] pagedCardPeopleResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.content[].peopleId").description("피플 ID"),
            fieldWithPath("data.content[].nickname").description("닉네임"),
            fieldWithPath("data.content[].profileImageUri").description("프로필 이미지 URI"),
            fieldWithPath("data.content[].completedProjectsCount").description("완수한 프로젝트 수"),
            fieldWithPath("data.content[].likesCount").description("받은 좋아요 수"),
            fieldWithPath("data.content[].profile.introduction").description("소개"),
            fieldWithPath("data.content[].profile.activityArea").description("활동 지역"),
            fieldWithPath("data.content[].profile.hashtags[]").description("해시태그")
        };
    }
}