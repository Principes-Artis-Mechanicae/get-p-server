package es.princip.getp.domain.common.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
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
