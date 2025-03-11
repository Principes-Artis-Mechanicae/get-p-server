package es.princip.getp.batch.project.apply;

import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertProjectApplicationJdbcService {

    private final BatchInsertProjectApplicationAttachmentFileJdbcService attachmentFileJdbcService;
    private final BatchInsertTeamProjectApplicationJdbcService teamJdbcService;
    private final BatchInsertIndividualProjectApplicationJdbcService individualJdbcService;
    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into project_application (
            project_application_id,
            expected_end_date,
            expected_start_date,
            description,
            status,
            people_id,
            project_id,
            dtype
        ) values (?, ?, ?, ?, ?, ?, ?, ?);
        """;

    public void batchUpdate(final List<ProjectApplication> applications) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final ProjectApplication application = applications.get(i);
                final String dtype = (application instanceof TeamProjectApplication) ?
                    TeamProjectApplication.TYPE : IndividualProjectApplication.TYPE;
                ps.setLong(1, application.getId().getValue());
                ps.setDate(2, Date.valueOf(application.getExpectedDuration().getEndDate()));
                ps.setDate(3, Date.valueOf(application.getExpectedDuration().getStartDate()));
                ps.setString(4, application.getDescription());
                ps.setString(5, application.getStatus().toString());
                ps.setLong(6, application.getApplicantId().getValue());
                ps.setLong(7, application.getProjectId().getValue());
                ps.setString(8,dtype);
            }

            @Override
            public int getBatchSize() {
                return applications.size();
            }
        });

        attachmentFileJdbcService.batchUpdate(applications);
        individualJdbcService.batchUpdate(applications);
        teamJdbcService.batchUpdate(applications);
    }
}
