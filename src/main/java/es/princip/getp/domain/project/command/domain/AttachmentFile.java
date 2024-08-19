package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.URL;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile {

    @Embedded
    private URL url;

    public AttachmentFile(final URL url) {
        this.url = url;
    }

    public static AttachmentFile from(final String url) {
        return new AttachmentFile(URL.from(url));
    }
}
