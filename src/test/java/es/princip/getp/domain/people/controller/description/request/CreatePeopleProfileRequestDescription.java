package es.princip.getp.domain.people.controller.description.request;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class CreatePeopleProfileRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = CreatePeopleProfileRequestDescription.class;
        return new FieldDescriptor[] {
            getDescriptor("education.school", "학력", clazz),
            getDescriptor("education.major", "전공", clazz),
            getDescriptor("activityArea", "활동 지역", clazz),
            getDescriptor("introduction", "소개", clazz),
            getDescriptor("techStacks[].value", "기술 스택",clazz),
            getDescriptor("portfolios[].uri", "포트폴리오 URI", clazz),
            getDescriptor("portfolios[].description", "포트폴리오 설명", clazz),
            getDescriptor("hashtags[].value", "해시태그", clazz)
        };
    }
}
