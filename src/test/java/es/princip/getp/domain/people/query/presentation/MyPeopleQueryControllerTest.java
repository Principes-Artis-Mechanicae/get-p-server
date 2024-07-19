package es.princip.getp.domain.people.query.presentation;

import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.command.presentation.PeopleErrorCodeController;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dto.people.PeopleResponse;
import es.princip.getp.domain.people.query.presentation.description.response.PeopleResponseDescription;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.security.details.PrincipalDetails;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({MyPeopleQueryController.class, PeopleErrorCodeController.class})
class MyPeopleQueryControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleDao peopleDao;

    @DisplayName("피플은 자신의 정보를 조회할 수 있다.")
    @Nested
    class GetMyPeople {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void getMyPeople(PrincipalDetails principalDetails) throws Exception {
            Long memberId = principalDetails.getMember().getMemberId();
            PeopleResponse response = new PeopleResponse(
                1L,
                EMAIL,
                NICKNAME,
                profileImage(1L).getUri(),
                PeopleType.INDIVIDUAL,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
            given(peopleDao.findByMemberId(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    responseFields(PeopleResponseDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void getMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}