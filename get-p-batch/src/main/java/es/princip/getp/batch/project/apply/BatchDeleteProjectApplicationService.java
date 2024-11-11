package es.princip.getp.batch.project.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchDeleteProjectApplicationService {

    private final JdbcTemplate jdbcTemplate;

    public void delete() {
        jdbcTemplate.execute("delete from team_project_application_teammate");
        jdbcTemplate.execute("delete from team_project_application");
        jdbcTemplate.execute("delete from individual_project_application");
        jdbcTemplate.execute("delete from project_application_attachment_file");
        jdbcTemplate.execute("delete from project_application");
        log.info("Table \"team_project_application_teammate\" is dropped");
        log.info("Table \"team_project_application\" is dropped");
        log.info("Table \"individual_project_application\" is dropped");
        log.info("Table \"project_application_attachment_file\" is dropped");
        log.info("Table \"project_application\" is dropped");
    }
}
