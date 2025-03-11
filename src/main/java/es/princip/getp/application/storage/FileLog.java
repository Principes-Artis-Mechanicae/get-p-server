package es.princip.getp.application.storage;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Getter
public class FileLog extends BaseModel {

    private static final String FILE_PREFIX = "files";

    private final Long id;
    @NotNull private final Long memberId;
    @NotBlank private final String filename;
    private final LocalDateTime uploadedAt;

    public FileLog(
        final Long id,
        final Long memberId,
        final String filename,
        final LocalDateTime uploadedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.filename = filename;
        this.uploadedAt = uploadedAt;

        validate();
    }

    private static String convertFilename(final String original) {
        return original.replace(" ", "_");
    }

    public static FileLog of(final Long memberId, final MultipartFile file) {
        final String filename = file.getOriginalFilename();
        assert filename != null;
        return new FileLog(null, memberId, convertFilename(filename), null);
    }

    public Path getPath() {
        return Paths.get(String.valueOf(memberId))
            .resolve(FILE_PREFIX)
            .resolve(String.valueOf(id))
            .resolve(filename);
    }
}
