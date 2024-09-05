package es.princip.getp.domain.common.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AttachmentFile extends BaseModel {

    @NotNull private final URL url;

    public AttachmentFile(final URL url) {
        this.url = url;

        validate();
    }

    public static AttachmentFile from(final String url) {
        return new AttachmentFile(URL.from(url));
    }
}
