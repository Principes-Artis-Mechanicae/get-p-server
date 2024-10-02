package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ProjectDetailResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("projectId", "프로젝트 ID"),
            getDescriptor("title", "제목"),
            getDescriptor("payment", "성공 보수"),
            getDescriptor("recruitmentCount", "모집 인원"),
            getDescriptor("applicantsCount", "지원자 수"),
            getDescriptor("applicationDuration.startDate", "지원자 모집 시작 날짜"),
            getDescriptor("applicationDuration.endDate", "지원자 모집 종료 날짜"),
            getDescriptor("estimatedDuration.startDate", "예상 작업 시작 날짜"),
            getDescriptor("estimatedDuration.endDate", "예상 작업 종료 날짜"),
            getDescriptor("description", "상세 설명"),
            getDescriptor("meetingType", "미팅 방식")
                .attributes(key("format").value("IN_PERSON, REMOTE")),
            getDescriptor("category", "카테고리")
                .attributes(key("format").value("BACKEND, FRONTEND, PROGRAM, WEB, ETC")),
            getDescriptor("status", "프로젝트 상태"),
            getDescriptor("attachmentFiles[]", "첨부 파일"),
            getDescriptor("hashtags[]", "해시태그"),
            getDescriptor("likesCount", "좋아요 수"),
            getDescriptor("client.clientId", "의뢰자 ID"),
            getDescriptor("client.nickname", "의뢰자 닉네임"),
            getDescriptor("client.address.zipcode", "의뢰자 우편번호"),
            getDescriptor("client.address.street", "의뢰자 도로명"),
            getDescriptor("client.address.detail", "의뢰자 상세 주소")
        };
    }
}
