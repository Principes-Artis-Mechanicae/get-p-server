package es.princip.getp.domain.people.presentation.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;


public class DetailPeopleProfileResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("education.school", "학력"),
            getDescriptor("education.major", "전공"),
            getDescriptor("activityArea", "활동 지역"),
            getDescriptor("introduction", "소개"),
            getDescriptor("techStacks[].value", "기술 스택"),
            getDescriptor("portfolios[].uri", "포트폴리오 URI"),
            getDescriptor("portfolios[].description", "포트폴리오 설명"),
            getDescriptor("hashtags[].value", "해시태그"),
            getDescriptor("completedProjectsCount", "완료한 프로젝트 수"),
            getDescriptor("interestsCount", "받은 관심 수")
        };
    }
}