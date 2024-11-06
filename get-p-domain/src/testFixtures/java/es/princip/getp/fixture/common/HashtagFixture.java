package es.princip.getp.fixture.common;

import es.princip.getp.domain.common.model.Hashtag;

import java.util.List;

public class HashtagFixture {

    public static List<Hashtag> hashtags() {
        return List.of(
            Hashtag.from("#해시태그1"),
            Hashtag.from("#해시태그2")
        );
    }
}
