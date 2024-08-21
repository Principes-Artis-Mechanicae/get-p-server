package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.ProfileImage;

import java.net.URI;

import static es.princip.getp.fixture.storage.StorageFixture.BASE_URI;

public class ProfileImageFixture {

    private static final String FILE_NAME = "image.jpg";

    public static ProfileImage profileImage(final Long memberId) {
        final String profileImageUri = String.format("/images/%d/profile/%s", memberId, FILE_NAME);
        final URI uri = URI.create(BASE_URI).resolve(profileImageUri);
        return ProfileImage.of(uri.toString());
    }
}
