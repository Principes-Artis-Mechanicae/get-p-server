package es.princip.getp.infra.storage.presentation;

import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.storage.application.FileUploadService;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static es.princip.getp.infra.storage.fixture.FileStorageFixture.DUMMY_TEXT;
import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileStorageController.class)
class FileStorageControllerTest extends AbstractControllerTest {

    @MockBean
    private FileUploadService fileUploadService;

    @Nested
    @DisplayName("파일 업로드")
    class UploadFile {

        final MockMultipartFile file = new MockMultipartFile("file", DUMMY_TEXT.getBytes());

        private ResultActions perform() throws Exception {
            return mockMvc.perform(multipart("/storage/files")
                .file(file)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser
        @DisplayName("사용자는 파일을 업로드할 수 있다.")
        void uploadFile() throws Exception {
            given(fileUploadService.uploadFile(any()))
                .willReturn(URI.create("https://storage.princip.es/files/1/test.txt"));

            perform()
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        requestParts(partWithName("file").description("업로드할 파일")),
                        responseFields(
                            getDescriptor("fileUri", "업로드된 파일 URI")
                        )
                    )
                )
                .andDo(print());
        }
    }
}