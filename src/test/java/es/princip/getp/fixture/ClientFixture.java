package es.princip.getp.fixture;

import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.member.domain.entity.Member;

public class ClientFixture {

    public static String NICKNAME = "knu12370";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "010-1234-5678";
    public static String PROFILE_IMAGE_URI = "https://example.com/img.jpg";
    public static String ADDRESS = "대구광역시 북구";
    public static String ACCOUNT_NUMBER = "3332-112-12-12";
    public static String UPDATED_NICKNAME = "scv1702";

    public static CreateClientRequest createClientRequest() {
        return new CreateClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PROFILE_IMAGE_URI,
            EMAIL,
            ACCOUNT_NUMBER);
    }

    public static UpdateClientRequest updateClientRequest() {
        return new UpdateClientRequest(
            UPDATED_NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PROFILE_IMAGE_URI,
            EMAIL,
            ACCOUNT_NUMBER);
    }

    public static Client createClient(Member member) {
        return Client.builder()
            .nickname(NICKNAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .address(ADDRESS)
            .accountNumber(ACCOUNT_NUMBER)
            .member(member)
            .build();
    }

    public static Client createClient() {
        return Client.builder()
            .nickname(NICKNAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .address(ADDRESS)
            .accountNumber(ACCOUNT_NUMBER)
            .member(MemberFixture.createMember())
            .build();
    }
}