package es.princip.getp.api.controller.member.fixture;

import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileFixture {

    public static MockMultipartFile imageMultiPartFile() {
        return new MockMultipartFile(
            "image",
            new byte[] {0x00, 0x01, 0x02, 0x03}
        );
    }
}
