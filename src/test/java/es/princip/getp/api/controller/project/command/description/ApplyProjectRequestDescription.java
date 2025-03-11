package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class ApplyProjectRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = ApplyProjectRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("expectedDuration.startDate", "예상 작업 시작 기간", clazz),
            getDescriptor("expectedDuration.endDate", "예상 작업 종료 기간", clazz),
            getDescriptor("description", "프로젝트 지원 설명", clazz),
            getDescriptor("attachmentFiles", "첨부 파일", clazz)
        };
    }
}
