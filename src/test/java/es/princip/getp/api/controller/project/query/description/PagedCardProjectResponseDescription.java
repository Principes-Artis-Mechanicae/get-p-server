package es.princip.getp.api.controller.project.query.description;

import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class PagedCardProjectResponseDescription {

    public static FieldDescriptor[] pagedCardProjectResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.content[].projectId").description("프로젝트 ID"),
            fieldWithPath("data.content[].title").description("제목"),
            fieldWithPath("data.content[].payment").description("성공 보수"),
            fieldWithPath("data.content[].recruitmentCount").description("모집 인원"),
            fieldWithPath("data.content[].applicantsCount").description("지원자 수"),
            fieldWithPath("data.content[].estimatedDays").description("예상 작업 일수"),
            fieldWithPath("data.content[].applicationDuration.startDate").description("지원자 모집 시작 기간"),
            fieldWithPath("data.content[].applicationDuration.endDate").description("지원자 모집 종료 기간"),
            fieldWithPath("data.content[].hashtags[]").description("해시태그"),
            fieldWithPath("data.content[].description").description("상세 설명"),
            fieldWithEnum(ProjectStatus.class).withPath("data.content[].status").description("프로젝트 상태")
        };
    }
}
