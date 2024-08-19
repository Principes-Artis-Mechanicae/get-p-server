package es.princip.getp.api.controller.member.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyMemberQueryControllerTest extends ControllerTest {

    @DisplayName("내 회원 정보 조회")
    @Nested
    class GetMyMember {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/member/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @DisplayName("사용자는 자신의 회원 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember() throws Exception {

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        responseFields(
                            getDescriptor("memberId", "회원 ID"),
                            getDescriptor("email", "이메일"),
                            getDescriptor("nickname", "닉네임"),
                            getDescriptor("profileImageUri", "프로필 사진 URI"),
                            getDescriptor("memberType", "회원 유형"),
                            getDescriptor("createdAt", "회원 가입일")
                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss")),
                            getDescriptor("updatedAt", "회원 정보 수정일")
                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                    )
                )
                .andDo(print());
        }
    }
}