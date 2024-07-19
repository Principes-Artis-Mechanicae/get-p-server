package es.princip.getp.domain.member.command.presentation;

import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.presentation.ErrorCodeController;
import es.princip.getp.infra.storage.exception.ImageErrorCode;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.infra.storage.fixture.ImageStorageFixture.imageMultiPartFile;
import static es.princip.getp.infra.util.ErrorCodeFields.errorCodeFields;
import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyMemberController.class, ErrorCodeController.class})
class MyMemberControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberService memberService;

    @Nested
    @DisplayName("프로필 업로드")
    class UploadProfileImage {

        private final Long memberId = 1L;

        @DisplayName("사용자는 프로필 사진을 올릴 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        public void uploadProfileImage() throws Exception {
            given(memberService.changeProfileImage(eq(memberId), any(MultipartFile.class)))
                .willReturn(profileImage(memberId).getUri());

            mockMvc.perform(multipart("/member/me/profile-image")
                .file(imageMultiPartFile())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
            )
            .andExpect(status().isCreated())
            .andDo(
                restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestParts(partWithName("image").description("프로필 이미지")),
                    responseFields(
                        getDescriptor("profileImageUri", "업로드된 프로필 사진의 URI")
                    )
                )
            )
            .andDo(print());
        }

        @Test
        void uploadProfileImageErrorCode() throws Exception {
            mockMvc.perform(get("/error-code/storage/images"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(errorCodeFields(ImageErrorCode.values())));
        }
    }
}