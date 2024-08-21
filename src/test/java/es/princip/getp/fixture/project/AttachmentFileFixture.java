package es.princip.getp.fixture.project;

import es.princip.getp.domain.project.command.domain.AttachmentFile;

import java.util.List;

public class AttachmentFileFixture {

    public static List<AttachmentFile> attachmentFiles() {
        return List.of(
            AttachmentFile.from("https://example.com/attachment1"),
            AttachmentFile.from("https://example.com/attachment2")
        );
    }
}
