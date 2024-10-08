package es.princip.getp.api.docs;

import org.springframework.restdocs.headers.HeaderDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;

public class HeaderDescriptorHelper {

    public static HeaderDescriptor authorizationHeaderDescriptor() {
        return headerWithName("Authorization").description("Bearer ${ACCESS_TOKEN}");
    }

    public static HeaderDescriptor refreshTokenHeaderDescriptor() {
        return headerWithName("Refresh-Token").description("Bearer ${REFRESH_TOKEN}");
    }
}
