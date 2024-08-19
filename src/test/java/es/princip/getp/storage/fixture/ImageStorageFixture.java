package es.princip.getp.storage.fixture;

import org.springframework.mock.web.MockMultipartFile;

public class ImageStorageFixture {

    public static MockMultipartFile imageMultiPartFile() {
        return new MockMultipartFile(
            "image",
            new byte[] {0x00, 0x01, 0x02, 0x03}
        );
    }
}
