package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectAsTeamRequest;
import es.princip.getp.domain.project.apply.model.ProjectApplicationType;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;

public class ApplyProjectAsTeamRequestDescription {

    public static FieldDescriptor[] applyProjectAsTeamRequestDescription() {
        final Class<?> clazz = ApplyProjectAsTeamRequest.class;
        return new FieldDescriptor[] {
            fieldWithEnum(ProjectApplicationType.class).withPath("type").description("지원 유형. 팀으로 지원할 경우 TEAM으로 설정"),
            fieldWithConstraint("expectedDuration.startDate", clazz).description("예상 작업 시작 기간"),
            fieldWithConstraint("expectedDuration.endDate", clazz).description("예상 작업 종료 기간"),
            fieldWithConstraint("description", clazz).description("지원 내용"),
            fieldWithConstraint("attachmentFiles", clazz).description("첨부 파일"),
            fieldWithConstraint("teammates", clazz).description("팀원 정보. 각 팀원의 피플 ID 리스트")
        };
    }
}
