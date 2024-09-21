package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;

import java.util.List;

public class IndividualProjectApplicationData extends ProjectApplicationData {

    public IndividualProjectApplicationData(
        final Duration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles
    ) {
        super(expectedDuration, description, attachmentFiles);
    }
}