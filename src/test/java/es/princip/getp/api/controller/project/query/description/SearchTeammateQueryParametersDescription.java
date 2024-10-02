package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class SearchTeammateQueryParametersDescription {

    public static ParameterDescriptor[] description(int pageSize) {
        return new ParameterDescriptor[] {
            parameterWithName("size").description("페이지 크기")
                .optional()
                .attributes(key("default").value(String.valueOf(pageSize))),
            parameterWithName("nickname").description("주어진 닉네임으로 시작하는 피플을 검색해요.")
                .optional()
                .attributes(key("default").value("null")),
            parameterWithName("cursor").description("페이지에 대한 커서. 첫 페이지를 조회할 땐 생략할 수 있어요.")
                .optional()
                .attributes(key("default").value("null"))
        };
    }
}
