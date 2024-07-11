package es.princip.getp.domain.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.client.fixture.ClientFixture;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.infra.config.SecurityConfig;
import es.princip.getp.infra.config.SecurityTestConfig;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.support.PrincipalDetailsParameterResolver;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyClientController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
@ExtendWith(PrincipalDetailsParameterResolver.class)
@DisplayName("의뢰자는")
class MyClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Nested
    @DisplayName("내 의뢰자 정보 조회")
    class GetMyClient {

        @Test
        @DisplayName("내 의뢰자 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getMyClient(PrincipalDetails principalDetails) throws Exception {
            //given
            Member member = principalDetails.getMember();
            Client client = ClientFixture.createClient(member);
            given(clientService.getByMemberId(member.getMemberId())).willReturn(client);

            //when and then
            mockMvc.perform(get("/client/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                    .value(200))
                .andExpect(jsonPath("$.data.client.clientId")
                    .value(client.getClientId()))
                .andExpect(jsonPath("$.data.client.nickname")
                    .value(client.getNickname()))
                .andExpect(jsonPath("$.data.client.email")
                    .value(client.getEmail()))
                .andExpect(jsonPath("$.data.client.phoneNumber")
                    .value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.data.client.profileImageUri")
                    .value(client.getMember().getProfileImageUri()))
                .andExpect(jsonPath("$.data.client.address.zipcode")
                    .value(client.getAddress().getZipcode()))
                .andExpect(jsonPath("$.data.client.address.street")
                    .value(client.getAddress().getStreet()))
                .andExpect(jsonPath("$.data.client.address.detail")
                    .value(client.getAddress().getDetail()))
                .andExpect(jsonPath("$.data.client.bankAccount.bank")
                    .value(client.getBankAccount().getBank()))
                .andExpect(jsonPath("$.data.client.bankAccount.accountNumber")
                    .value(client.getBankAccount().getAccountNumber()))
                .andExpect(jsonPath("$.data.client.bankAccount.accountHolder")
                    .value(client.getBankAccount().getAccountHolder()))
                .andExpect(jsonPath("$.data.member.memberId")
                    .value(client.getMember().getMemberId()))
                .andExpect(jsonPath("$.data.member.email")
                    .value(client.getMember().getEmail()))
                .andExpect(jsonPath("$.data.member.memberType")
                    .value(client.getMember().getMemberType().name()));
        }

        @Test
        @DisplayName("의뢰자 정보를 등록하지 않으면 내 의뢰자 정보를 조회할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getMyClient_WhenMyClientDoesNotExists_Fail(PrincipalDetails principalDetails) throws Exception {
            //given
            Member member = principalDetails.getMember();
            given(clientService.getByMemberId(member.getMemberId()))
                .willThrow(new BusinessLogicException(ClientErrorCode.CLIENT_NOT_FOUND));

            //when and then
            mockMvc.perform(get("/client/me"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("내 의뢰자 정보 수정")
    class Update {

        @Test
        @DisplayName("내 의뢰자 정보를 수정할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void update(PrincipalDetails principalDetails) throws Exception {
            //given
            Member member = principalDetails.getMember();
            UpdateClientRequest request = ClientFixture.updateClientRequest();
            Client client = ClientFixture.createClient(member, request);
            given(clientService.update(any(), any())).willReturn(client);

            //when and then
            mockMvc.perform(put("/client/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(print());
        }

        @Test
        @DisplayName("의뢰자 정보를 등록하지 않으면 내 의뢰자 정보를 수정할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void update_WhenMyClientDoesNotExists_Fail() throws Exception {
            //given
            UpdateClientRequest request = ClientFixture.updateClientRequest();
            given(clientService.update(any(), any())).willThrow(new BusinessLogicException(ClientErrorCode.CLIENT_NOT_FOUND));

            //when and then
            mockMvc.perform(put("/client/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andDo(print());
        }
    }
}