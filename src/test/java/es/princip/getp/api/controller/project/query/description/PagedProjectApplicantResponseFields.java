package es.princip.getp.api.controller.project.query.description;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

public class PagedProjectApplicantResponseFields {

    public static FieldDescriptor[] pagedProjectApplicantResponseFields() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.content[].peopleId").description("지원자의 피플 ID"),
            fieldWithPath("data.content[].nickname").description("지원자의 닉네임"),
            fieldWithPath("data.content[].profileImageUrl").description("지원자의 프로필 이미지 URL"),
            fieldWithPath("data.content[].education").description("학력"),
            fieldWithPath("data.content[].education.school").description("학교명"),
            fieldWithPath("data.content[].education.major").description("전공명"),
            fieldWithEnum(ProjectApplicationStatus.class).withPath("data.content[].status")
                .description(
                """
                지원 상태
                PENDING_TEAM_APPROVAL: 팀원 승인 대기
                
                COMPLETED: 지원 완료
                
                WAITING_MEETING: 미팅 준비
               
                MEETING_COMPLETED: 미팅 완료
                
                ACCEPTED: 진행 확정
                
                CLOSED: 모집 마감
                """
            ),
            subsectionWithPath("data.content[].teammates")
                .optional()
                .description("팀원 목록"),
            fieldWithPath("data.content[].teammates[].peopleId")
                .type(JsonFieldType.NUMBER)
                .description("팀원의 피플 ID"),
            fieldWithPath("data.content[].teammates[].nickname")
                .type(JsonFieldType.STRING)
                .description("팀원의 닉네임"),
            fieldWithPath("data.content[].teammates[].profileImageUrl")
                .type(JsonFieldType.STRING)
                .description("팀원의 프로필 이미지 URL"),
        };
    }
}
