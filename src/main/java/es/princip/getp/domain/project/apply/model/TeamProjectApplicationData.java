package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamProjectApplicationData extends ProjectApplicationData {

    private final List<PeopleId> teams;

    public TeamProjectApplicationData(
        final Duration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final List<PeopleId> teams
    ) {
        super(expectedDuration, description, attachmentFiles);
        this.teams = teams;
    }
}