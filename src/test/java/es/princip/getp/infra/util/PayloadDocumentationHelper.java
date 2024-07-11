package es.princip.getp.infra.util;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;

public class PayloadDocumentationHelper {

    public static ResponseFieldsSnippet responseFields(FieldDescriptor... descriptors) {
        return org.springframework.restdocs.payload.PayloadDocumentation.responseFields(
            beneathPath("data").withSubsectionId("data")
        ).and(descriptors);
    }
}
