package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;


public class PagedDetailPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("content[].peopleId", "피플 ID"),
            getDescriptor("content[].nickname", "닉네임"),
            getDescriptor("content[].profileImageUri", "프로필 이미지 URI"),
            getDescriptor("content[].completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("content[].likesCount", "받은 좋아요 수"),
            getDescriptor("content[].profile.introduction", "소개"),
            getDescriptor("content[].profile.activityArea", "활동 지역"),
            getDescriptor("content[].profile.techStacks[]", "기술 스택"),
            getDescriptor("content[].profile.education.school", "학교"),
            getDescriptor("content[].profile.education.major", "전공"),
            getDescriptor("content[].profile.hashtags[]", "해시태그"),
            getDescriptor("content[].profile.portfolios[].url", "포트폴리오 URL"),
            getDescriptor("content[].profile.portfolios[].description", "포트폴리오 설명")
        };
    }
}