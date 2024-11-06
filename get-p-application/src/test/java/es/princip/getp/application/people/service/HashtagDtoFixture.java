package es.princip.getp.application.people.service;

import es.princip.getp.domain.common.model.Hashtag;

import java.util.List;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;

public class HashtagDtoFixture {

    public static List<String> hashtagsRequest() {
        return hashtags().stream()
            .map(Hashtag::getValue)
            .toList();
    }

    public static List<String> hashtagsResponse() {
        return hashtags().stream()
            .map(Hashtag::getValue)
            .toList();
    }
}
