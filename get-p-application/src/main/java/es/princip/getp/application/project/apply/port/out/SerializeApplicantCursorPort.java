package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.application.project.apply.dto.response.ProjectApplicantResponse;
import org.springframework.data.domain.Slice;

public interface SerializeApplicantCursorPort {

    String serializeCursor(Slice<ProjectApplicantResponse> slice);
}
