package es.princip.getp.api.docs;

import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class StatusFieldDescriptor {

    public static FieldDescriptor statusField() {
        return fieldWithPath("status").description("응답 상태");
    }
}
