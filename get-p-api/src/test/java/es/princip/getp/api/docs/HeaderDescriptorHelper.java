package es.princip.getp.api.docs;

import org.springframework.restdocs.headers.HeaderDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;

public class HeaderDescriptorHelper {

    public static HeaderDescriptor authorizationHeaderDescription() {
        return headerWithName("Authorization").description("Bearer ${ACCESS_TOKEN}");
    }

    public static HeaderDescriptor refreshTokenHeaderDescription() {
        return headerWithName("Refresh-Token").description("Bearer ${REFRESH_TOKEN}");
    }
}
