package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;

public class CommissionProjectRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = CommissionProjectRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("title", "제목", clazz),
            getDescriptor("payment", "성공 보수", clazz),
            getDescriptor("recruitmentCount", "모집 인원", clazz),
            getDescriptor("applicationDuration.startDate", "지원자 모집 시작 기간", clazz),
            getDescriptor("applicationDuration.endDate", "지원자 모집 종료 기간", clazz),
            getDescriptor("estimatedDuration.startDate", "예상 작업 시작 기간", clazz),
            getDescriptor("estimatedDuration.endDate", "예상 작업 종료 기간", clazz),
            getDescriptor("description", "상세 설명", clazz),
            getDescriptor("meetingType", "미팅 방식", clazz)
                .attributes(key("format").value("IN_PERSON, REMOTE")),
            getDescriptor("category", "카테고리", clazz)
                .attributes(key("format").value("BACKEND, FRONTEND, PROGRAM, WEB, ETC")),
            getDescriptor("attachmentFiles[]", "첨부 파일", clazz),
            getDescriptor("hashtags[]", "해시태그", clazz)
        };
    }
}
