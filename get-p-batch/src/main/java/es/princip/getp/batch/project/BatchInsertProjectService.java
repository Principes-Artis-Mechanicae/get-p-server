package es.princip.getp.batch.project;

import es.princip.getp.batch.config.ExtendsWithExecutionTimer;
import es.princip.getp.domain.project.commission.model.Project;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BatchInsertProjectService {

    private final JdbcTemplate jdbcTemplate;
    private final int BATCH_SIZE = 1000;

    @ExtendsWithExecutionTimer
    public void batchInsert(final List<Project> projects) {
        final String sql =
            """
            insert into project (
                project_id,
                application_end_date,
                application_start_date,
                estimated_end_date,
                estimated_start_date,
                payment,
                category,
                description,
                meeting_type,
                status,
                title,
                client_id,
                recruitment_count,
                created_at,
                updated_at
            )
            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        final int batchSize = Math.min(BATCH_SIZE, projects.size());

        for (int i = 0; i < projects.size(); i += batchSize) {
            final int end = Math.min(i + batchSize, projects.size());
            final List<Project> batchList = projects.subList(i, end);

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                    final Project project = batchList.get(i);
                    ps.setLong(1, project.getId().getValue());
                    ps.setDate(2, Date.valueOf(project.getApplicationDuration().getEndDate()));
                    ps.setDate(3, Date.valueOf(project.getApplicationDuration().getStartDate()));
                    ps.setDate(4, Date.valueOf(project.getEstimatedDuration().getEndDate()));
                    ps.setDate(5, Date.valueOf(project.getEstimatedDuration().getStartDate()));
                    ps.setLong(6, project.getPayment());
                    ps.setString(7, project.getCategory().toString());
                    ps.setString(8, project.getDescription());
                    ps.setString(9, project.getMeetingType().toString());
                    ps.setString(10, project.getStatus().toString());
                    ps.setString(11, project.getTitle());
                    ps.setLong(12, project.getClientId().getValue());
                    ps.setLong(13, project.getRecruitmentCount());
                    ps.setString(14, String.valueOf(LocalDateTime.now()));
                    ps.setString(15, String.valueOf(LocalDateTime.now()));
                }

                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
            });

            log.info("Batch inserted: {} to {}", i, end - 1);
            log.info("Total gain: {}%", (i + batchSize) * 100D / projects.size());
        }

        log.info("Bach completed");
    }
}
