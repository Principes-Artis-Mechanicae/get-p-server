package es.princip.getp.batch.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DropProjectService {

    private final JdbcTemplate jdbcTemplate;

    public void dropProject() {
        jdbcTemplate.execute("delete from project");
        log.info("Table \"project\" is dropped");
    }
}
