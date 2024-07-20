package es.princip.getp.domain.member.command.infra;

import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.ProfileImage;
import es.princip.getp.domain.member.command.domain.service.ProfileImageService;
import es.princip.getp.domain.member.command.exception.FailedToSaveProfileImageException;
import es.princip.getp.infra.storage.ImageStorage;
import es.princip.getp.infra.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {

    public static final String PROFILE_IMAGE_PREFIX = "profile";

    private final ImageStorage imageStorage;

    public ProfileImage saveProfileImage(final Member member, final MultipartFile image) {
        final Path destination = getPathToSaveProfileImage(member, image);
        try (InputStream in = image.getInputStream()) {
            final String uri = imageStorage.storeImage(destination, in);
            return ProfileImage.of(uri);
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
        final Path path = Paths.get(profileImage.getUri());
        imageStorage.deleteImage(path);
    }
}
