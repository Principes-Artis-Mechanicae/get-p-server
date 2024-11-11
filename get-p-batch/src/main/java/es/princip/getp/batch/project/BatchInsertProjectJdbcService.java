package es.princip.getp.batch.project;

import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertProjectJdbcService {

    private final BatchInsertProjectHashtagJdbcService hashtagJdbcService;
    private final BatchInsertProjectAttachmentFileJdbcService attachmentFileJdbcService;
    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
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

    public void batchUpdate(final List<Project> projects) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final Project project = projects.get(i);
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
                return projects.size();
            }
        });
        hashtagJdbcService.batchUpdate(projects);
        attachmentFileJdbcService.batchUpdate(projects);
    }
}
