package es.princip.getp.infrastructure.adapter.storage.fixure;

import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileFixture {

    public static MockMultipartFile fileMultiPartFile() {
        return new MockMultipartFile(
            "file",
            "dummy".getBytes()
        );
    }
}
