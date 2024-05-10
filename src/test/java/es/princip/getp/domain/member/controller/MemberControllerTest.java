package es.princip.getp.domain.member.controller;

import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.fixture.ClientFixture.createClient;
import static es.princip.getp.fixture.PeopleFixture.createPeople;
import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends AbstractControllerTest {

    @Nested
    @DisplayName("사용자는 내 회원 정보를 조회할 수 있다.")
    class GetMyMember {

        ResultActions perform() throws Exception {
            return mockMvc.perform(get("/member/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                )
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(
                            headerWithName("Authorization").description("Bearer ${ACCESS_TOKEN}")
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"))
                            .and(
                                getDescriptor("memberId", "회원 ID"),
                                getDescriptor("email", "이메일"),
                                getDescriptor("nickname", "닉네임"),
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

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember_WhenMemberTypeIsPeople(PrincipalDetails principalDetails) throws Exception {
            createPeople(principalDetails.getMember());

            perform();
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void getMyMember_WhenMemberTypeIsClient(PrincipalDetails principalDetails) throws Exception {
            createClient(principalDetails.getMember());

            perform();
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember_WhenPeopleIsNotRegistered() throws Exception {
            perform()
                .andExpect(jsonPath("$.data.nickname").isEmpty());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void getMyMember_WhenClientIsNotRegistered() throws Exception {
            perform()
                .andExpect(jsonPath("$.data.nickname").isEmpty());
        }
    }
}