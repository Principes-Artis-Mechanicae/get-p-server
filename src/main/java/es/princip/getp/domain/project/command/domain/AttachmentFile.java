package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.URI;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile {

    @Embedded
    private URI uri;

    private AttachmentFile(final URI uri) {
        this.uri = uri;
    }

    public static AttachmentFile from(final String uri) {
        return new AttachmentFile(URI.from(uri));
    }
}
