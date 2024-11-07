package es.princip.getp.api.controller.project.query.description;

import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class DetailProjectResponseDescription {

    public static FieldDescriptor[] detailProjectResponseDescription() {
        return new FieldDescriptor[] {
            statusField(),
            fieldWithPath("data.projectId").description("프로젝트 ID"),
            fieldWithPath("data.title").description("제목"),
            fieldWithPath("data.payment").description("성공 보수"),
            fieldWithPath("data.recruitmentCount").description("모집 인원"),
            fieldWithPath("data.applicantsCount").description("지원자 수"),
            fieldWithPath("data.applicationDuration.startDate").description("지원자 모집 시작 날짜"),
            fieldWithPath("data.applicationDuration.endDate").description("지원자 모집 종료 날짜"),
            fieldWithPath("data.estimatedDuration.startDate").description("예상 작업 시작 날짜"),
            fieldWithPath("data.estimatedDuration.endDate").description("예상 작업 종료 날짜"),
            fieldWithPath("data.description").description("상세 설명"),
            fieldWithEnum(MeetingType.class).withPath("data.meetingType").description("미팅 방식"),
            fieldWithEnum(ProjectCategory.class).withPath("data.category").description("카테고리"),
            fieldWithEnum(ProjectStatus.class).withPath("data.status").description("프로젝트 상태"),
            fieldWithPath("data.attachmentFiles[]").description("첨부 파일"),
            fieldWithPath("data.hashtags[]").description("해시태그"),
            fieldWithPath("data.likesCount").description("좋아요 수"),
            fieldWithPath("data.liked").description("좋아요 여부"),
            fieldWithPath("data.client.clientId").description("의뢰자 ID"),
            fieldWithPath("data.client.nickname").description("의뢰자 닉네임"),
            fieldWithPath("data.client.address.zipcode").description("의뢰자 우편번호"),
            fieldWithPath("data.client.address.street").description("의뢰자 도로명"),
            fieldWithPath("data.client.address.detail").description("의뢰자 상세 주소")
        };
    }
}
