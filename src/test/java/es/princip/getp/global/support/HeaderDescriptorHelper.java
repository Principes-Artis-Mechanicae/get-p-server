package es.princip.getp.global.support;

import org.springframework.restdocs.headers.HeaderDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;

public class HeaderDescriptorHelper {

    public static HeaderDescriptor authorizationHeaderDescriptor() {
        return headerWithName("Authorization").description("Bearer ${ACCESS_TOKEN}");
    }
}
