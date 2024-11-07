package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectAsIndividualRequest;
import es.princip.getp.domain.project.apply.model.ProjectApplicationType;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;

public class ApplyProjectAsIndividualRequestDescription {

    public static FieldDescriptor[] applyProjectAsIndividualRequestDescription() {
        final Class<?> clazz = ApplyProjectAsIndividualRequest.class;
        return new FieldDescriptor[] {
            fieldWithEnum(ProjectApplicationType.class).withPath("type").description("지원 유형. 개인으로 지원할 경우 INDIVIDUAL로 설정"),
            fieldWithConstraint("expectedDuration.startDate", clazz).description("예상 작업 시작 기간"),
            fieldWithConstraint("expectedDuration.endDate", clazz).description("예상 작업 종료 기간"),
            fieldWithConstraint("description", clazz).description("지원 내용"),
            fieldWithConstraint("attachmentFiles", clazz).description("첨부 파일")
        };
    }
}
