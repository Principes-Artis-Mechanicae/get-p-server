package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class SearchTeammateResponseDescription {

    public static FieldDescriptor[] searchTeammateResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.content[].peopleId").description("피플 ID"),
            fieldWithPath("data.content[].nickname").description("닉네임"),
            fieldWithPath("data.content[].profileImageUri").description("프로필 이미지 URI")
        };
    }
}