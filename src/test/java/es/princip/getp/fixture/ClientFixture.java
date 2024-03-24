package es.princip.getp.fixture;

import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.global.domain.values.Address;
import es.princip.getp.global.domain.values.BankAccount;

public class ClientFixture {

    public static String NICKNAME = "knu12370";
    public static String EMAIL = "knu12370@princip.es";
    public static String PHONE_NUMBER = "01012345678";
    public static String PROFILE_IMAGE_URI = "/knu12370/profile/example.jpg";
    public static Address ADDRESS =
        Address.of("41566", "대구광역시 북구 대학로 80", "IT대학 융복합관");
    public static BankAccount BANK_ACCOUNT =
        BankAccount.of("대구은행", "123456789", "유지훈");
    public static String UPDATED_NICKNAME = "scv1702";

    public static CreateClientRequest createClientRequest() {
        return new CreateClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PROFILE_IMAGE_URI,
            ADDRESS,
            BANK_ACCOUNT);
    }

    public static UpdateClientRequest updateClientRequest() {
        return new UpdateClientRequest(
            UPDATED_NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PROFILE_IMAGE_URI,
            ADDRESS,
            BANK_ACCOUNT);
    }

    public static Client createClient(Member member) {
        return Client.builder()
            .nickname(NICKNAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .address(ADDRESS)
            .bankAccount(BANK_ACCOUNT)
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
            .bankAccount(BANK_ACCOUNT)
            .member(MemberFixture.createMember())
            .build();
    }

    public static Client createClient(Member member, UpdateClientRequest request) {
        return Client.builder()
            .nickname(request.nickname())
            .email(request.email())
            .phoneNumber(request.phoneNumber())
            .profileImageUri(request.profileImageUri())
            .address(request.address())
            .bankAccount(request.bankAccount())
            .member(member)
            .build();
    }
}