package es.princip.getp.api.docs;

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
