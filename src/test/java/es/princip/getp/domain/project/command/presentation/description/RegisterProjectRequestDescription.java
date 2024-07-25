package es.princip.getp.domain.project.command.presentation.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class RegisterProjectRequestDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("title", "제목"),
            getDescriptor("payment", "금액"),
            getDescriptor("applicationDuration.startDate", "지원자 모집 시작 기간"),
            getDescriptor("applicationDuration.endDate", "지원자 모집 종료 기간"),
            getDescriptor("estimatedDuration.startDate", "예상 작업 시작 기간"),
            getDescriptor("estimatedDuration.endDate", "예상 작업 종료 기간"),
            getDescriptor("description", "설명"),
            getDescriptor("meetingType", "미팅 방식"),
            getDescriptor("category", "카테고리"),
            getDescriptor("attachmentFiles[]", "첨부 파일"),
            getDescriptor("hashtags[]", "해시태그")
        };
    }
}
