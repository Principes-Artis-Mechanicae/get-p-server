package es.princip.getp.api.controller.project.query.description;

import es.princip.getp.domain.member.model.MemberType;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class GetProjectsQueryParametersDescription {

    public static ParameterDescriptor[] description(int page, int pageSize) {
        return new ParameterDescriptor[] {
            parameterWithName("page").description("페이지 번호")
                .optional()
                .attributes(key("default").value(String.valueOf(page))),
            parameterWithName("size").description("페이지 크기")
                .optional()
                .attributes(key("default").value(String.valueOf(pageSize))),
            parameterWithName("sort").description("정렬 조건")
                .optional()
                .attributes(key("default").value("projectId,desc")),
            parameterWithName("liked").description("좋아요한 프로젝트만 보기 여부. 필터 설정 시 true로 설정")
                .optional()
                .attributes(key("default").value("null"))
                .attributes(key("permission").value(MemberType.ROLE_PEOPLE)),
            parameterWithName("commissioned").description("의뢰한 프로젝트만 보기 여부. 필터 설정 시 true로 설정")
                .optional()
                .attributes(key("default").value("null"))
                .attributes(key("permission").value(MemberType.ROLE_CLIENT)),
            parameterWithName("applied").description("지원한 프로젝트만 보기 여부. 필터 설정 시 true로 설정")
                .optional()
                .attributes(key("default").value("null"))
                .attributes(key("permission").value(MemberType.ROLE_PEOPLE)),
            parameterWithName("closed").description("모집 마감된 프로젝트만 보기 여부. 필터 설정 시 true로 설정")
                .optional()
                .attributes(key("default").value("null"))
        };
    }
}
