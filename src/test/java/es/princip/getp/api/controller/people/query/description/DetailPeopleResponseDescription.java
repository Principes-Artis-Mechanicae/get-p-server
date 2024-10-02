package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


public class DetailPeopleResponseDescription {

    public static FieldDescriptor[] detailPeopleResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.peopleId").description("피플 ID"),
            fieldWithPath("data.nickname").description("닉네임"),
            fieldWithPath("data.profileImageUri").description("프로필 이미지 URI"),
            fieldWithPath("data.completedProjectsCount").description("완수한 프로젝트 수"),
            fieldWithPath("data.likesCount").description("받은 좋아요 수"),
            fieldWithPath("data.liked").description("좋아요 여부"),
            fieldWithPath("data.profile.introduction").description("소개"),
            fieldWithPath("data.profile.activityArea").description("활동 지역"),
            fieldWithPath("data.profile.techStacks[]").description("기술 스택"),
            fieldWithPath("data.profile.education.school").description("학교"),
            fieldWithPath("data.profile.education.major").description("전공"),
            fieldWithPath("data.profile.hashtags[]").description("해시태그"),
            fieldWithPath("data.profile.portfolios[].url").description("포트폴리오 URL"),
            fieldWithPath("data.profile.portfolios[].description").description("포트폴리오 설명")
        };
    }
}