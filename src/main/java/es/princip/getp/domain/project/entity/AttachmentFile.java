package es.princip.getp.domain.project.entity;

import es.princip.getp.global.exception.BadRequestErrorCode;
import es.princip.getp.global.exception.BusinessLogicException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile {

    private static final Pattern ATTACHMENT_URI_PATTERN = Pattern.compile("^(https://).*");

    @Column(name = "uri")
    private String uri;

    private AttachmentFile(String uri) {
        validate(uri);
        this.uri = uri;
    }

    private void validate(String uri) {
        if (!ATTACHMENT_URI_PATTERN.matcher(uri).matches()) {
            throw new BusinessLogicException(BadRequestErrorCode.WRONG_FILE_URI);
        }
    }

    public static AttachmentFile from(String uri) {
        return new AttachmentFile(uri);
    }
}
