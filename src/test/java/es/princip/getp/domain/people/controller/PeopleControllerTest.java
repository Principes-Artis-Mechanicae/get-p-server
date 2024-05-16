package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.fixture.PeopleFixture;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.domain.member.domain.enums.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.domain.enums.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeopleRequest;
import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PeopleController.class, PeopleErrorCodeController.class})
class PeopleControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleService;

    @DisplayName("피플은 피플 정보를 등록할 수 있다.")
    @Nested
    class CreatePeople {
        private final CreatePeopleRequest request = createPeopleRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/people")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void createPeople(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = Mockito.spy(PeopleFixture.createPeople(member));
            given(people.getPeopleId()).willReturn(1L);
            given(peopleService.create(member, request)).willReturn(people);

            perform()
                .andDo(restDocs.document(
                        requestHeaders(
                            authorizationHeaderDescriptor()
                        ),
                        requestFields(
                            getDescriptor("nickname", "닉네임", CreatePeopleRequest.class),
                            getDescriptor("email", "이메일(기본값은 회원 가입 시 기입한 이메일)", CreatePeopleRequest.class),
                            getDescriptor("phoneNumber", "전화번호", CreatePeopleRequest.class),
                            getDescriptor("peopleType", "피플 타입", CreatePeopleRequest.class)
                                .attributes(key("format").value("TEAM, INDIVIDUAL")),
                            getDescriptor("profileImageUri", "프로필 이미지 URI", CreatePeopleRequest.class)
                                .attributes(key("format").value("/images/{memberId}/profile/{fileName}"))
                        ),
                        responseHeaders(
                            headerWithName("Location").description("생성된 피플 정보 URI")
                        ),
                        responseFields(
                            getDescriptor("peopleId", "피플 ID")
                        )
                    )
                )
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void createPeople_WhenMemberTypeIsClient_ShouldFail() {
            assertThatThrownBy(() -> perform()
                .andExpect(status().isForbidden())
                .andDo(print())).hasCauseInstanceOf(AccessDeniedException.class);
        }
    }
}