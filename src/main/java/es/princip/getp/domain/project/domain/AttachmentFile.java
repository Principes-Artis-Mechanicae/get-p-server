package es.princip.getp.domain.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile {

    @Column(name = "uri")
    private String uri;

    private AttachmentFile(String uri) {
        this.uri = uri;
    }

    public static AttachmentFile from(String uri) {
        return new AttachmentFile(uri);
    }
}
