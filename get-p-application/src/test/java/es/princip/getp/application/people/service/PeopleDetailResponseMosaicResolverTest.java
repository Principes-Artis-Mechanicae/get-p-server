package es.princip.getp.application.people.service;

import es.princip.getp.application.people.dto.response.people.PeopleDetailResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PortfolioResponse;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.Education;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import static es.princip.getp.application.common.fixture.HashtagDtoFixture.hashtagsResponse;
import static es.princip.getp.application.people.fixture.PortfolioResponseFixture.portfolioResponses;
import static es.princip.getp.application.common.fixture.TechStackDtoFixture.techStacksResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PeopleDetailResponseMosaicResolverTest {

    @Mock private MessageSource messageSource;

    @InjectMocks private PeopleDetailResponseMosaicResolver resolver;

    private static final String MESSAGE = "로그인 후 이용해주세요.";
    private final MemberId memberId = new MemberId(1L);

    private final PeopleDetailResponse response = new PeopleDetailResponse(
        1L,
        NICKNAME,
        profileImage(memberId).getUrl(),
        0,
        0,
        true,
        new PeopleProfileDetailResponse(
            introduction(),
            activityArea(),
            education(),
            techStacksResponse(),
            hashtagsResponse(),
            portfolioResponses()
        )
    );

    @BeforeEach
    void setup() {
        given(messageSource.getMessage("restricted.access", null, Locale.getDefault()))
            .willReturn(MESSAGE);
    }

    private void assertEducation(Education actual, Education expected) {
        assertSoftly(education -> {
            education.assertThat(actual.getMajor())
                .hasSameSizeAs(expected.getMajor());
            education.assertThat(actual.getSchool())
                .hasSameSizeAs(expected.getSchool());
        });
    }

    private void assertPortfolios(List<PortfolioResponse> actual, List<PortfolioResponse> expected) {
        assertSoftly(portfolios -> {
            portfolios.assertThat(actual)
                .extracting(portfolio -> portfolio.description().length())
                .containsExactlyElementsOf(expected
                    .stream()
                    .map(portfolio -> portfolio.description().length())
                    .toList());
            portfolios.assertThat(actual)
                .extracting(portfolio -> portfolio.url().length())
                .containsExactlyElementsOf(expected
                    .stream()
                    .map(portfolio -> portfolio.url().length())
                    .toList());
        });
    }

    @Test
    void 피플_상세_정보를_모자이크_한다() {
        final PeopleDetailResponse mosaicResponse = resolver.resolve(response);
        
        assertSoftly(peopleProfile -> {
            peopleProfile.assertThat(mosaicResponse.getProfile().getIntroduction())
                .hasSameSizeAs(response.getProfile().getIntroduction());
            peopleProfile.assertThat(mosaicResponse.getProfile().getActivityArea())
                .hasSameSizeAs(response.getProfile().getActivityArea());
            peopleProfile.assertThat(mosaicResponse.getProfile().getTechStacks())
                .extracting(String::length)
                .containsExactlyElementsOf(response.getProfile().getTechStacks()
                    .stream()
                    .map(String::length)
                    .toList());
            assertEducation(mosaicResponse.getProfile().getEducation(), response.getProfile().getEducation());
            assertPortfolios(mosaicResponse.getProfile().getPortfolios(), response.getProfile().getPortfolios());
        });
    }
}