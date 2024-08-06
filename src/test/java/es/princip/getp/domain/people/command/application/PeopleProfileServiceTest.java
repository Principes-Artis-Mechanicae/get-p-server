package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.people.command.domain.PeopleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PeopleProfileServiceTest {

    @InjectMocks
    private PeopleProfileService peopleProfileService;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("writeProfile()은")
    class WriteProfile {

        @Test
        @DisplayName("피플 프로필을 작성한다.")
        void writeProfile() {

        }
    }

    @Nested
    @DisplayName("editProfile()은")
    class EditProfile {

        @Test
        @DisplayName("피플 프로필을 수정한다.")
        void update() {
        }
    }
}
