package es.princip.getp.infra.util;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ParameterDescriptorHelper {
    public static ParameterDescriptor getDescriptor(String name, String description, String key, String value) {
        return parameterWithName(name).description(description)
        .optional()
        .attributes(key(key).value(value));
    }
}
