package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ProjectApplicationData {

    private final Duration expectedDuration;
    private final String description;
    private final List<AttachmentFile> attachmentFiles;
}
