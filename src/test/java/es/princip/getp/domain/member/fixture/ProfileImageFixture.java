package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.model.ProfileImage;

public class ProfileImageFixture {

    private static final String FILE_NAME = "image.jpg";

    public static ProfileImage profileImage(final Long memberId) {
        final String profileImage = String.format("/images/profile/%d/%s", memberId, FILE_NAME);
        return ProfileImage.of(profileImage);
    }
}
