package es.princip.getp.domain.member.model;

import es.princip.getp.domain.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ProfileImage extends BaseModel {

    @NotNull private final String url;

    public ProfileImage(final String url) {
        this.url = url;

        validate();
    }

    public static ProfileImage of(final String url) {
        return new ProfileImage(url);
    }
}
