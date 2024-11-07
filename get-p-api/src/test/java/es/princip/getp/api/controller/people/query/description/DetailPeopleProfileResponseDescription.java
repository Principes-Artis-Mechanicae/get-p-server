package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


public class DetailPeopleProfileResponseDescription {

    public static FieldDescriptor[] detailPeopleProfileResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.education.school").description("학력"),
            fieldWithPath("data.education.major").description("전공"),
            fieldWithPath("data.activityArea").description("활동 지역"),
            fieldWithPath("data.introduction").description("소개"),
            fieldWithPath("data.techStacks[]").description("기술 스택"),
            fieldWithPath("data.portfolios[].description").description("포트폴리오 설명"),
            fieldWithPath("data.portfolios[].url").description("포트폴리오 URL"),
            fieldWithPath("data.hashtags[]").description("해시태그")
        };
    }
}