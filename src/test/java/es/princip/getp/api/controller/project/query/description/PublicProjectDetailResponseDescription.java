package es.princip.getp.api.controller.project.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class PublicProjectDetailResponseDescription {

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
            getDescriptor("description", "비로그인 상세 설명 : 이 정보는 로그인 후에만 접근할 수 있습니다."),
            getDescriptor("meetingType", "미팅 방식"),
            getDescriptor("category", "카테고리"),
            getDescriptor("hashtags[]", "해시태그"),
            getDescriptor("likesCount", "좋아요 수"),
        };
    }
}
