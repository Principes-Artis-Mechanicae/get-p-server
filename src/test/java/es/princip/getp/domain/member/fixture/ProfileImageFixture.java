package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.command.domain.model.ProfileImage;

public class ProfileImageFixture {

    private static final String FILE_NAME = "image.jpg";

    public static ProfileImage profileImage(final Long memberId) {
        final String profileImage = String.format("/images/%d/profile/%s", memberId, FILE_NAME);
        return ProfileImage.of(profileImage);
    }
}
