package es.princip.getp.api.controller.people.command.description.request;

import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleProfileRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class RegisterPeopleProfileRequestDescription {

    public static FieldDescriptor[] registerPeopleProfileRequestDescription() {
        final Class<?> clazz = RegisterPeopleProfileRequest.class;
        return new FieldDescriptor[] {
            fieldWithPath("education").description("학력"),
            fieldWithConstraint("education.school", clazz)
                .optional()
                .description("학교명"),
            fieldWithConstraint("education.major", clazz).description("전공명"),
            fieldWithConstraint("activityArea", clazz).description("활동 지역"),
            fieldWithConstraint("introduction", clazz)
                .optional()
                .description("소개"),
            fieldWithConstraint("techStacks[]", clazz).description("기술 스택"),
            fieldWithConstraint("portfolios[].url", clazz).description("포트폴리오 URL"),
            fieldWithConstraint("portfolios[].description", clazz).description("포트폴리오 설명"),
            fieldWithConstraint("hashtags[]", clazz).description("해시태그")
        };
    }
}
