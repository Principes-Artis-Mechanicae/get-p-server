package es.princip.getp.domain.people.query.presentation.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.docs.FieldDescriptorHelper.getDescriptor;


public class DetailPeopleProfileResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("education.school", "학력"),
            getDescriptor("education.major", "전공"),
            getDescriptor("activityArea", "활동 지역"),
            getDescriptor("introduction", "소개"),
            getDescriptor("techStacks[]", "기술 스택"),
            getDescriptor("portfolios[].description", "포트폴리오 설명"),
            getDescriptor("portfolios[].url", "포트폴리오 URL"),
            getDescriptor("hashtags[]", "해시태그")
        };
    }
}