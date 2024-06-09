package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.service.PeopleProfileService;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.controller.ErrorCodeController;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static es.princip.getp.domain.member.domain.enums.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyPeopleController.class, ErrorCodeController.class})
class MyPeopleControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleService;

    @MockBean
    private PeopleProfileService peopleProfileService;

    @DisplayName("피플은 자신의 피플 정보를 조회할 수 있다.")
    @Nested
    class GetMyPeople {

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        void getMyPeople(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));

            given(people.getPeopleId()).willReturn(1L);
            given(people.getCreatedAt()).willReturn(LocalDateTime.now());
            given(people.getUpdatedAt()).willReturn(LocalDateTime.now());

            given(peopleService.getByMemberId(anyLong())).willReturn(people);

            mockMvc.perform(get("/people/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        responseFields(
                            getDescriptor("peopleId", "피플 ID"),
                            getDescriptor("nickname", "닉네임"),
                            getDescriptor("email", "이메일(기본값은 회원 가입 시 기입한 이메일)"),
                            getDescriptor("phoneNumber", "전화번호"),
                            getDescriptor("peopleType", "피플 유형"),
                            getDescriptor("profileImageUri", "프로필 이미지 URI"),
                            getDescriptor("createdAt", "피플 정보 등록일"),
                            getDescriptor("updatedAt", "피플 정보 수정일")
                        )
                    )
                )
                .andDo(print());
        }
    }
}