package es.princip.getp.api.docs;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ParameterDescriptorHelper {

    public static ParameterDescriptor getDescriptor(
        final String name,
        final String description,
        final String key,
        final String value
    ) {
        return parameterWithName(name).description(description)
            .optional()
            .attributes(key(key).value(value));
    }
}
