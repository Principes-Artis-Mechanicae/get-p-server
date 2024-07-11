package es.princip.getp.domain.people.presentation.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;


public class DetailPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID"),
            getDescriptor("nickname", "닉네임"),
            getDescriptor("peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("profileImageUri", "프로필 이미지 URI"),
            getDescriptor("profile.introduction", "소개"),
            getDescriptor("profile.activityArea", "활동 지역"),
            getDescriptor("profile.techStacks[].value", "기술 스택"),
            getDescriptor("profile.education.school", "학교"),
            getDescriptor("profile.education.major", "전공"),
            getDescriptor("profile.hashtags[].value", "해시태그"),
            getDescriptor("profile.completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("profile.interestsCount", "받은 관심 수"),
            getDescriptor("profile.portfolios[].uri", "포트폴리오 URI"),
            getDescriptor("profile.portfolios[].description", "포트폴리오 설명")
        };
    }
}