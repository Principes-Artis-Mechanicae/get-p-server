package es.princip.getp.application.people.service;

import static org.mockito.BDDMockito.given;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosResponse;

import es.princip.getp.api.controller.people.query.dto.people.PeopleDetailResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.domain.member.model.MemberId;

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
                    portfoliosResponse()
                )
            );

    @BeforeEach
    void setup() {
        given(messageSource.getMessage("restricted.access", null, Locale.getDefault()))
            .willReturn(MESSAGE);
    }

    @Test
    void 피플_상세_정보를_모자이크_한다() {
        final PeopleDetailResponse mosaicResponse = resolver.resolve(response);

        assertSoftly(peopleProfile -> {
            peopleProfile.assertThat(mosaicResponse.getProfile().getIntroduction()).hasSameSizeAs(response.getProfile().getIntroduction());
            peopleProfile.assertThat(mosaicResponse.getProfile().getActivityArea()).hasSameSizeAs(response.getProfile().getActivityArea());
            peopleProfile.assertThat(mosaicResponse.getProfile().getTechStacks())
            .extracting(String::length)
            .containsExactlyElementsOf(response.getProfile().getTechStacks()
                .stream()
                .map(String::length)
                .toList());
            assertSoftly(education -> {
                education.assertThat(mosaicResponse.getProfile().getEducation().getMajor()).hasSameSizeAs(response.getProfile().getEducation().getMajor());
                education.assertThat(mosaicResponse.getProfile().getEducation().getSchool()).hasSameSizeAs(response.getProfile().getEducation().getSchool());
            });
            assertSoftly(portfolios -> {
                portfolios.assertThat(mosaicResponse.getProfile().getPortfolios())
                .extracting(portfolio -> portfolio.description().length())
                .containsExactlyElementsOf(response.getProfile().getPortfolios()
                    .stream()
                    .map(portfolio -> portfolio.description().length())
                    .toList());
                portfolios.assertThat(mosaicResponse.getProfile().getPortfolios())
                .extracting(portfolio -> portfolio.url().length())
                .containsExactlyElementsOf(response.getProfile().getPortfolios()
                    .stream()
                    .map(portfolio -> portfolio.url().length())
                    .toList());
            });
        });
    }
}