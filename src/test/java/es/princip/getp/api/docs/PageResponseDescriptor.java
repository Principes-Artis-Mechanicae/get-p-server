package es.princip.getp.api.docs;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class PageResponseDescriptor {

    public static FieldDescriptor[] pageResponseFieldDescriptors() {
        return new FieldDescriptor[] {
            getDescriptor("pageInfo.totalPages", "전체 페이지 수"),
            getDescriptor("pageInfo.totalElements", "전체 요소 수"),
            getDescriptor("pageInfo.size", "페이지 크기"),
            getDescriptor("pageInfo.number", "현재 페이지 번호"),
            getDescriptor("pageInfo.numberOfElements", "현재 페이지 요소 수"),
            getDescriptor("pageInfo.first", "첫 페이지 여부"),
            getDescriptor("pageInfo.last", "마지막 페이지 여부"),
            getDescriptor("pageInfo.empty", "비어있는 페이지 여부"),
            getDescriptor("pageInfo.sort.property", "정렬 속성"),
            getDescriptor("pageInfo.sort.direction", "정렬 방향")
        };
    }
}
