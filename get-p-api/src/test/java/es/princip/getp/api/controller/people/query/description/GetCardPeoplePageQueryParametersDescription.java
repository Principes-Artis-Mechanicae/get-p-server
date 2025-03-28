package es.princip.getp.api.controller.people.query.description;

import es.princip.getp.domain.member.model.MemberType;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class GetCardPeoplePageQueryParametersDescription {

    public static ParameterDescriptor[] getCardPeoplePageQueryParametersDescription(int page, int pageSize) {
        return new ParameterDescriptor[] {
            parameterWithName("page").description("페이지 번호")
                .optional()
                .attributes(key("default").value(String.valueOf(page))),
            parameterWithName("size").description("페이지 크기")
                .optional()
                .attributes(key("default").value(String.valueOf(pageSize))),
            parameterWithName("sort").description("정렬 조건")
                .optional()
                .attributes(key("default").value("peopleId,desc")),
            parameterWithName("liked").description("좋아요한 피플만 보기 여부. 필터 설정 시 true로 설정")
                .optional()
                .attributes(key("default").value("null"))
                .attributes(key("permission").value(MemberType.ROLE_CLIENT)),
            parameterWithName("keyword").description("피플 닉네임으로 검색하기. 닉네임이 해당 키워드로 시작하는 피플을 검색해요.")
                .optional()
                .attributes(key("default").value("null"))
        };
    }
}
