package es.princip.getp.api.controller.project.command.description;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectAsTeamRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ApplyProjectAsTeamRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = ApplyProjectAsTeamRequest.class;
        return new FieldDescriptor[] {
            getDescriptor("type", "지원 유형", clazz)
                .attributes(key("format").value("TEAM")),
            getDescriptor("expectedDuration.startDate", "예상 작업 시작 기간", clazz),
            getDescriptor("expectedDuration.endDate", "예상 작업 종료 기간", clazz),
            getDescriptor("description", "지원 내용", clazz),
            getDescriptor("attachmentFiles", "첨부 파일", clazz),
            getDescriptor("teammates", "팀원 정보. 각 팀원의 피플 ID 리스트", clazz)
        };
    }
}
