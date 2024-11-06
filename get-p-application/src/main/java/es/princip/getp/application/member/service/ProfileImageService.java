package es.princip.getp.application.member.service;

import es.princip.getp.application.member.dto.command.RegisterProfileImageCommand;
import es.princip.getp.application.member.exception.NotSupportedProfileImageExtensionException;
import es.princip.getp.application.member.port.in.ProfileImageUseCase;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.member.port.out.UpdateMemberPort;
import es.princip.getp.application.storage.port.out.DeleteFilePort;
import es.princip.getp.application.storage.port.out.StoreFilePort;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.ProfileImage;
import es.princip.getp.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
class ProfileImageService implements ProfileImageUseCase {

    private static final String PROFILE_IMAGE_PREFIX = "profile";

    private final LoadMemberPort loadMemberPort;
    private final UpdateMemberPort updateMemberPort;

    private final StoreFilePort storeFilePort;
    private final DeleteFilePort deleteFilePort;

    private static final List<String> whiteImageExtensionList = Arrays.asList(
        "image/jpeg",
        "image/pjpeg",
        "image/png",
        "image/bmp",
        "image/x-windows-bmp"
    );

    @Override
    @Transactional
    public String registerProfileImage(final RegisterProfileImageCommand command) {
        final MemberId memberId = command.memberId();
        final Member member = loadMemberPort.loadBy(memberId);
        if (member.hasProfileImage()) {
            deleteProfileImage(member.getProfileImage());
        }
        final MultipartFile image = command.image();
        final ProfileImage profileImage = saveProfileImage(member, image);
        member.registerProfileImage(profileImage);
        updateMemberPort.update(member);
        return profileImage.getUrl();
    }

    private ProfileImage saveProfileImage(final Member member, final MultipartFile image) {
        if (!whiteImageExtensionList.contains(image.getContentType())) {
            throw new NotSupportedProfileImageExtensionException();
        }
        final Path destination = getPathToSaveProfileImage(member, image);
        final URI uri = storeFilePort.store(image, destination);
        return ProfileImage.from(uri.toString());
    }

    private Path getPathToSaveProfileImage(final Member member, final MultipartFile image) {
        final String memberId = String.valueOf(member.getId());
        final String fileName = StringUtil.generateRandomFilename(image.getOriginalFilename());
        return Paths.get(memberId).resolve(PROFILE_IMAGE_PREFIX).resolve(fileName);
    }

    private void deleteProfileImage(final ProfileImage profileImage) {
        deleteFilePort.delete(URI.create(profileImage.getUrl()));
    }
}
