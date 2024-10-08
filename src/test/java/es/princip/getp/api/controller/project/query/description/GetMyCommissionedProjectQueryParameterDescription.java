package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class GetMyCommissionedProjectQueryParameterDescription {

    public static ParameterDescriptor[] description() {
        return new ParameterDescriptor[] {
            parameterWithName("page").description("페이지 번호")
                .optional()
                .attributes(key("default").value("0")),
            parameterWithName("size").description("페이지 크기")
                .optional()
                .attributes(key("default").value("10")),
            parameterWithName("sort").description("정렬 방식")
                .optional()
                .attributes(key("default").value("projectId,desc")),
            parameterWithName("cancelled").description("만료된 프로젝트 보기 여부")
                .optional()
                .attributes(key("default").value("false"))
        };
    }
}
