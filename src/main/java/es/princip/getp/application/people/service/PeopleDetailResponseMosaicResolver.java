package es.princip.getp.application.people.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import es.princip.getp.api.controller.people.query.dto.people.PeopleDetailResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.application.support.MosaicResolver;
import es.princip.getp.application.support.MosaicResolverSupport;
import es.princip.getp.domain.people.model.Education;

@Service
class PeopleDetailResponseMosaicResolver extends MosaicResolverSupport
    implements MosaicResolver<PeopleDetailResponse> {

    @Autowired
    protected PeopleDetailResponseMosaicResolver(MessageSource messageSource) {
        super(messageSource);
    }

    private Education mosaicEducation(final Education education) {
        final String school = mosaicMessage(education.getSchool());
        final String major = mosaicMessage(education.getMajor());
        return new Education(school, major);
    }

    private List<PortfolioResponse> mosaicPortfolioResponse(final List<PortfolioResponse> portfolios) {
        return portfolios.stream()
            .map(portfolio -> new PortfolioResponse(
                mosaicMessage(portfolio.description()),
                mosaicMessage(portfolio.url())
            ))
            .toList();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PeopleDetailResponse.class.equals(clazz);
    }

    @Override
    public PeopleDetailResponse resolve(PeopleDetailResponse response) {
        PeopleProfileDetailResponse mosaicResponse = response.getProfile();
        final String introduction = mosaicMessage(mosaicResponse.getIntroduction());
        final String activityArea = mosaicMessage(mosaicResponse.getActivityArea());
        final Education education = mosaicEducation(mosaicResponse.getEducation());
        final List<String> techStacks = mosaicMessage(mosaicResponse.getHashtags());
        final List<PortfolioResponse> portfolios = mosaicPortfolioResponse(mosaicResponse.getPortfolios());
        mosaicResponse.mosaic(introduction, activityArea, education, techStacks, portfolios);

        response.mosaic(mosaicResponse);
        return response;
    }    
}
