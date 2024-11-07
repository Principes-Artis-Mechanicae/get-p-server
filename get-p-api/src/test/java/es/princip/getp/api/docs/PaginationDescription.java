package es.princip.getp.api.docs;


import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class PaginationDescription {
    
    public static ParameterDescriptor[] cursorPaginationParameters(final int size) {
        return new ParameterDescriptor[] {
            parameterWithName("size").description("페이지 크기")
                .optional()
                .attributes(key("default").value(String.valueOf(size))),
            parameterWithName("cursor").description("페이지에 대한 커서. 첫 페이지를 조회할 땐 생략할 수 있어요.")
                .optional()
                .attributes(key("default").value("null"))
        };
    }
}
