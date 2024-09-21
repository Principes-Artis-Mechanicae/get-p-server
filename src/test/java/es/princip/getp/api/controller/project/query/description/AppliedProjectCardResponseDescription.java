package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class AppliedProjectCardResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("content[].projectId", "프로젝트 ID"),
            getDescriptor("content[].title", "제목"),
            getDescriptor("content[].payment", "성공 보수"),
            getDescriptor("content[].recruitmentCount", "모집 인원"),
            getDescriptor("content[].applicantsCount", "지원자 수"),
            getDescriptor("content[].estimatedDays", "예상 작업 일수"),
            getDescriptor("content[].applicationDuration.startDate", "지원자 모집 시작 기간"),
            getDescriptor("content[].applicationDuration.endDate", "지원자 모집 종료 기간"),
            getDescriptor("content[].hashtags[]", "해시태그"),
            getDescriptor("content[].description", "상세 설명"),
            getDescriptor("content[].status", "프로젝트 상태")
        };
    }
}
