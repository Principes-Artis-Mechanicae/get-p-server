package es.princip.getp.domain.project.query.presentation.description;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class GetApplicantsByProjectIdQueryParameterDescription {

    public static ParameterDescriptor[] description() {
        return new ParameterDescriptor[] {
            parameterWithName("page").description("페이지 번호")
                .optional().attributes(key("default").value("0")),
            parameterWithName("size").description("페이지 크기")
                .optional().attributes(key("default").value("10")),
            parameterWithName("sort").description("정렬 방식")
                .optional().attributes(key("default").value("peopleId,desc"))
        };
    }
}
