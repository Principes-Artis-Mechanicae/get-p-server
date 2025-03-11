package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;


public class DetailPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID"),
            getDescriptor("nickname", "닉네임"),
            getDescriptor("profileImageUri", "프로필 이미지 URI"),
            getDescriptor("peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("likesCount", "받은 좋아요 수"),
            getDescriptor("profile.introduction", "소개"),
            getDescriptor("profile.activityArea", "활동 지역"),
            getDescriptor("profile.techStacks[]", "기술 스택"),
            getDescriptor("profile.education.school", "학교"),
            getDescriptor("profile.education.major", "전공"),
            getDescriptor("profile.hashtags[]", "해시태그"),
            getDescriptor("profile.portfolios[].url", "포트폴리오 URL"),
            getDescriptor("profile.portfolios[].description", "포트폴리오 설명")
        };
    }
}