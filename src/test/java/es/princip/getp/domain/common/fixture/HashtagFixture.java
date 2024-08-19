package es.princip.getp.domain.common.fixture;

import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.common.dto.HashtagsResponse;

import java.util.List;

public class HashtagFixture {

    public static List<Hashtag> hashtags() {
        return List.of(
            Hashtag.from("#해시태그1"),
            Hashtag.from("#해시태그2")
        );
    }

    public static List<String> hashtagsRequest() {
        return List.of(
            "#해시태그1",
            "#해시태그2"
        );
    }

    public static HashtagsResponse hashtagsResponse() {
        return HashtagsResponse.from(
            List.of(
                Hashtag.from("#해시태그1"),
                Hashtag.from("#해시태그2")
            )
        );
    }
}
