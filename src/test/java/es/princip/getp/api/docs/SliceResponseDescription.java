package es.princip.getp.api.docs;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class SliceResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("sliceInfo.size", "페이지 크기"),
            getDescriptor("sliceInfo.numberOfElements", "현재 페이지 요소 수"),
            getDescriptor("sliceInfo.first", "첫 페이지 여부"),
            getDescriptor("sliceInfo.last", "마지막 페이지 여부"),
            getDescriptor("sliceInfo.empty", "비어있는 페이지 여부"),
            getDescriptor("sliceInfo.cursor", "다음 페이지에 대한 커서. 다음 페이지를 요청할 때 이전 페이지 응답에서 받은 커서 값을 넣어주세요."),
            getDescriptor("sliceInfo.sort", "정렬 정보")
                .ignored(),
            getDescriptor("sliceInfo.sort.property", "정렬 속성")
                .optional()
                .type(JsonFieldType.STRING),
            getDescriptor("sliceInfo.sort.direction", "정렬 방향")
                .optional()
                .type(JsonFieldType.STRING)
        };
    }
}
