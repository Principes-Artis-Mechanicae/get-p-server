package es.princip.getp.api.docs;

import org.springframework.data.domain.Sort;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class PageResponseDescriptor {

    public static FieldDescriptor[] pageResponseFieldDescriptors() {
        return new FieldDescriptor[] {
            fieldWithPath("data.pageInfo.totalPages").description("전체 페이지 수"),
            fieldWithPath("data.pageInfo.totalElements").description("전체 요소 수"),
            fieldWithPath("data.pageInfo.size").description("페이지 크기"),
            fieldWithPath("data.pageInfo.number").description("현재 페이지 번호"),
            fieldWithPath("data.pageInfo.numberOfElements").description("현재 페이지 요소 수"),
            fieldWithPath("data.pageInfo.first").description("첫 페이지 여부"),
            fieldWithPath("data.pageInfo.last").description("마지막 페이지 여부"),
            fieldWithPath("data.pageInfo.empty").description("비어있는 페이지 여부"),
            fieldWithPath("data.pageInfo.sort.property").description("정렬 속성"),
            fieldWithEnum(Sort.Direction.class).withPath("data.pageInfo.sort.direction").description("정렬 방향"),
        };
    }
}
