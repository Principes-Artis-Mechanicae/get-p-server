package es.princip.getp.domain.common.infra;

import es.princip.getp.domain.common.domain.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {URLMapperImpl.class})
class URLMapperTest {

    @Autowired
    private URLMapper urlMapper;

    @Test
    void stringToURL() {
        final URL url = urlMapper.stringToURL("https://url.com");

        assertThat(url).isNotNull();
    }
}