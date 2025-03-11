package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;

public class CommissionProjectRequestDescription {

    public static FieldDescriptor[] commissionProjectRequestDescription() {
        final Class<?> clazz = CommissionProjectRequest.class;
        return new FieldDescriptor[]{
            fieldWithConstraint("title", clazz).description("제목"),
            fieldWithConstraint("payment", clazz).description("성공 보수"),
            fieldWithConstraint("recruitmentCount", clazz).description("모집 인원"),
            fieldWithConstraint("applicationDuration.startDate", clazz).description("지원자 모집 시작 기간"),
            fieldWithConstraint("applicationDuration.endDate", clazz).description("지원자 모집 종료 기간"),
            fieldWithConstraint("estimatedDuration.startDate", clazz).description("예상 작업 시작 기간"),
            fieldWithConstraint("estimatedDuration.endDate", clazz).description("예상 작업 종료 기간"),
            fieldWithConstraint("description", clazz).description("상세 설명"),
            fieldWithEnum(MeetingType.class).withPath("meetingType").description("미팅 방식"),
            fieldWithEnum(ProjectCategory.class).withPath("category").description("카테고리"),
            fieldWithConstraint("attachmentFiles[]", clazz).description("첨부 파일"),
            fieldWithConstraint("hashtags[]", clazz).description("해시태그"),
        };
    }
}
