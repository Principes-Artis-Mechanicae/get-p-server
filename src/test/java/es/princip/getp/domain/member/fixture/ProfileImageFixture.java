package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.ProfileImage;

import java.net.URI;

import static es.princip.getp.domain.member.domain.service.ProfileImageService.PROFILE_IMAGE_PREFIX;
import static es.princip.getp.domain.storage.fixture.ImageStorageFixture.IMAGE_STORAGE_PATH;

public class ProfileImageFixture {

    private static final String FILE_NAME = "image.jpg";

    public static ProfileImage profileImage(final Member member) {
        final String memberId = String.valueOf(member.getMemberId());
        final URI profileImage = IMAGE_STORAGE_PATH.resolve(PROFILE_IMAGE_PREFIX)
            .resolve(memberId)
            .resolve(FILE_NAME)
            .toUri();
        return ProfileImage.of(profileImage);
    }
}
