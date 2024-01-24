package es.princip.getp.domain.client.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.fixture.ClientFixture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ClientRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Client가 DB에 저장 되는지 확인한다")
    void testSave() {
        Member testMember = MemberFixture.createMember();
        Client testClient = ClientFixture.createClientByMember(testMember);
        clientRepository.save(testClient);
    }

    @Test
    @DisplayName("멤버 ID로 피플 계정을 조회한다.")
    void testFindByMember_MemberId() {
        Member savedMember = memberRepository.save(MemberFixture.createMember());
        Client savedClient = clientRepository.save(ClientFixture.createClientByMember(savedMember));

        Client result = clientRepository.findByMember_MemberId(savedMember.getMemberId())
                                            .orElseThrow(() -> new BusinessLogicException(ClientErrorCode.CLIENT_NOT_FOUND));

        assertThat(result.getClientId())
                .isEqualTo(savedClient.getClientId());
    }
}