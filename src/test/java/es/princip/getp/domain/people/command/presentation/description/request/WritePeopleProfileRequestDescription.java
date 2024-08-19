package es.princip.getp.domain.people.command.presentation.description.request;

import es.princip.getp.domain.people.command.presentation.dto.request.RegisterPeopleProfileRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.docs.FieldDescriptorHelper.getDescriptor;

public class WritePeopleProfileRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = RegisterPeopleProfileRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("education.school", "학력", clazz),
            getDescriptor("education.major", "전공", clazz),
            getDescriptor("activityArea", "활동 지역", clazz),
            getDescriptor("introduction", "소개", clazz),
            getDescriptor("techStacks[]", "기술 스택", clazz),
            getDescriptor("portfolios[].url", "포트폴리오 URL", clazz),
            getDescriptor("portfolios[].description", "포트폴리오 설명", clazz),
            getDescriptor("hashtags[]", "해시태그", clazz)
        };
    }
}
