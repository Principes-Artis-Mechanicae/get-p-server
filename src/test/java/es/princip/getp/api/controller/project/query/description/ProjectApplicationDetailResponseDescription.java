package es.princip.getp.api.controller.project.query.description;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.apply.model.ProjectApplicationType;
import es.princip.getp.domain.project.apply.model.TeammateStatus;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ProjectApplicationDetailResponseDescription {

    public static FieldDescriptor[] projectApplicationDetailResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.applicationId").description("프로젝트 지원 ID"),
            fieldWithEnum(ProjectApplicationType.class).withPath("data.type").description("프로젝트 지원 타입"),
            fieldWithPath("data.project").description("지원한 프로젝트"),
            fieldWithPath("data.project.projectId").description("지원한 프로젝트 ID"),
            fieldWithPath("data.project.title").description("지원한 프로젝트 제목"),
            fieldWithPath("data.project.payment").description("지원한 프로젝트 성공 보수"),
            fieldWithPath("data.project.recruitmentCount").description("지원한 프로젝트 모집 인원"),
            fieldWithPath("data.project.applicantsCount").description("지원한 프로젝트 지원자 수"),
            fieldWithPath("data.project.applicationDuration.startDate").description("지원한 프로젝트 지원자 모집 시작 날짜"),
            fieldWithPath("data.project.applicationDuration.endDate").description("지원한 프로젝트 지원자 모집 종료 날짜"),
            fieldWithPath("data.project.estimatedDuration.startDate").description("지원한 프로젝트 예상 작업 시작 날짜"),
            fieldWithPath("data.project.estimatedDuration.endDate").description("지원한 프로젝트 예상 작업 종료 날짜"),
            fieldWithPath("data.project.description").description("지원한 프로젝트 상세 설명"),
            fieldWithEnum(MeetingType.class).withPath("data.project.meetingType").description("지원한 프로젝트 미팅 방식"),
            fieldWithEnum(ProjectCategory.class).withPath("data.project.category").description("지원한 프로젝트 카테고리"),
            fieldWithEnum(ProjectStatus.class).withPath("data.project.status").description("지원한 프로젝트 프로젝트 상태"),
            fieldWithPath("data.project.attachmentFiles[]").description("지원한 프로젝트 첨부 파일"),
            fieldWithPath("data.project.hashtags[]").description("지원한 프로젝트 해시태그"),
            fieldWithPath("data.project.likesCount").description("지원한 프로젝트 좋아요 수"),
            fieldWithPath("data.project.liked").description("지원한 프로젝트 좋아요 여부"),
            fieldWithPath("data.project.client.clientId").description("지원한 프로젝트 의뢰자 ID"),
            fieldWithPath("data.project.client.nickname").description("지원한 프로젝트 의뢰자 닉네임"),
            fieldWithPath("data.project.client.address.zipcode").description("지원한 프로젝트 의뢰자 우편번호"),
            fieldWithPath("data.project.client.address.street").description("지원한 프로젝트 의뢰자 도로명"),
            fieldWithPath("data.project.client.address.detail").description("지원한 프로젝트 의뢰자 상세 주소"),
            fieldWithPath("data.expectedDuration.startDate").description("예상 작업 시작 날짜"),
            fieldWithPath("data.expectedDuration.endDate").description("예상 작업 종료 날짜"),
            fieldWithEnum(ProjectApplicationStatus.class).withPath("data.status").description("프로젝트 지원 상태"),
            fieldWithPath("data.description").description("지원 내용"),
            fieldWithPath("data.attachmentFiles[]").description("첨부 파일"),
            fieldWithPath("data.teammates[]").optional().description("팀원 목록"),
            fieldWithPath("data.teammates[].peopleId").optional().description("팀원 피플 ID"),
            fieldWithPath("data.teammates[].nickname").optional().description("팀원 닉네임"),
            fieldWithEnum(TeammateStatus.class).withPath("data.teammates[].status").optional().description("팀원 상태"),
            fieldWithPath("data.teammates[].profileImageUri").optional().description("팀원 프로필 이미지 URI")
        };
    }
}
