package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class SearchTeammateResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("content[].peopleId", "피플 ID"),
            getDescriptor("content[].nickname", "닉네임"),
            getDescriptor("content[].profileImageUri", "프로필 이미지 URI")
        };
    }
}