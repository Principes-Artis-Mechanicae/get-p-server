package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.URL;
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
    private URL url;

    private AttachmentFile(final URL url) {
        this.url = url;
    }

    public static AttachmentFile from(final String url) {
        return new AttachmentFile(URL.from(url));
    }
}
