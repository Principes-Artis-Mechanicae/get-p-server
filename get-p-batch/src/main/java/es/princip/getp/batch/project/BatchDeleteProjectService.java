package es.princip.getp.batch.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchDeleteProjectService {

    private final JdbcTemplate jdbcTemplate;

    public void delete() {
        jdbcTemplate.execute("delete from project_hashtag");
        jdbcTemplate.execute("delete from project_attachment_file");
        jdbcTemplate.execute("delete from project");
        log.info("Table \"project_hashtag\" is dropped");
        log.info("Table \"project_attachment_file\" is dropped");
        log.info("Table \"project\" is dropped");
    }
}
