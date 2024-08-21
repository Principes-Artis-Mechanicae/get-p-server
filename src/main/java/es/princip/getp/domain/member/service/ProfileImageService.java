package es.princip.getp.domain.member.service;

import es.princip.getp.common.util.ImageUtil;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.ProfileImage;
import es.princip.getp.domain.member.exception.FailedToSaveProfileImageException;
import es.princip.getp.storage.application.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    public static final String PROFILE_IMAGE_PREFIX = "profile";

    private final ImageStorage imageStorage;

    private static final List<String> whiteImageExtensionList = Arrays.asList(
        "image/jpeg",
        "image/pjpeg",
        "image/png",
        "image/bmp",
        "image/x-windows-bmp"
    );

    public ProfileImage saveProfileImage(final Member member, final MultipartFile image) {
        if (!whiteImageExtensionList.contains(image.getContentType())) {
            throw new FailedToSaveProfileImageException();
        }
        final Path destination = getPathToSaveProfileImage(member, image);
        try (InputStream in = image.getInputStream()) {
            final URI uri = imageStorage.storeImage(destination, in);
            return ProfileImage.of(uri.toString());
        } catch (IOException exception) {
            throw new FailedToSaveProfileImageException();
        }
    }

    private Path getPathToSaveProfileImage(final Member member, final MultipartFile image) {
        final String memberId = String.valueOf(member.getMemberId());
        final String fileName = ImageUtil.generateRandomFilename(image.getOriginalFilename());
        return Paths.get(memberId).resolve(PROFILE_IMAGE_PREFIX).resolve(fileName);
    }

    public void deleteProfileImage(final ProfileImage profileImage) {
        imageStorage.deleteImage(URI.create(profileImage.getUrl()));
    }
}