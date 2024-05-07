package es.princip.getp.domain.member.controller;

import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleService;



    @MockBean
    private ClientService clientService;

    @Nested
    @DisplayName("사용자는")
    class GetMyMember {

        @DisplayName("내 회원 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            Long memberId = member.getMemberId();
            People people = PeopleFixture.createPeople(member);
            given(peopleService.getByMemberId(memberId)).willReturn(people);

            mockMvc.perform(get("/member/me")
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
    }
}