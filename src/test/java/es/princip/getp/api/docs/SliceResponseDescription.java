package es.princip.getp.api.docs;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class SliceResponseDescription {

    public static FieldDescriptor[] sliceResponseDescription() {
        return new FieldDescriptor[] {
            fieldWithPath("data.sliceInfo.size").description("페이지 크기"),
            fieldWithPath("data.sliceInfo.numberOfElements").description("현재 페이지 요소 수"),
            fieldWithPath("data.sliceInfo.first").description("첫 페이지 여부"),
            fieldWithPath("data.sliceInfo.last").description("마지막 페이지 여부"),
            fieldWithPath("data.sliceInfo.empty").description("비어있는 페이지 여부"),
            fieldWithPath("data.sliceInfo.cursor").description("다음 페이지에 대한 커서. 다음 페이지를 요청할 때 이전 페이지 응답에서 받은 커서 값을 넣어주세요."),
            fieldWithPath("data.sliceInfo.sort").description("정렬 정보").ignored(),
            fieldWithPath("data.sliceInfo.sort.property").description("정렬 속성").optional().type(JsonFieldType.STRING),
            fieldWithPath("data.sliceInfo.sort.direction").description("정렬 방향").optional().type(JsonFieldType.STRING)
        };
    }
}
