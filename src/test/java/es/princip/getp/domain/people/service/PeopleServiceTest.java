package es.princip.getp.domain.people.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberType;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequestDTO;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequestDTO;
import es.princip.getp.domain.people.dto.response.PeopleResponseDTO;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("피플 정보 등록")
    void testCreate() {
        //given
        Member member = new Member("hello@example.com", "hello", MemberType.ROLE_PEOPLE);
        CreatePeopleRequestDTO testRequestDTO = new CreatePeopleRequestDTO("홍길동", "hello@example.com", "010-1234-5678", "ROLE_INDIVIDUAL", "https://he.princip.es/img", "3332-112-12-12");
        People testPeople = testRequestDTO.toEntity(member);

        //when
        when(peopleRepository.save(any(People.class))).thenReturn(testPeople);
        People result = peopleRepository.save(testPeople);

        //then
        assertEquals(testPeople, result);
    }
}
