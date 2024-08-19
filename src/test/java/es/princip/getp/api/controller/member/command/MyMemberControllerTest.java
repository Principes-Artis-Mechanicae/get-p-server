package es.princip.getp.api.controller.member.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.storage.fixture.ImageStorageFixture.imageMultiPartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyMemberControllerTest extends ControllerTest {

    @Autowired
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
    }
}