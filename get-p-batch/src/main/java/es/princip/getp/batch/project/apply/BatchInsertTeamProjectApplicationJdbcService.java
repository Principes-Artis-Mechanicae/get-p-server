package es.princip.getp.batch.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertTeamProjectApplicationJdbcService {

    private final BatchInsertTeamProjectApplicationTeammateJdbcService teammateJdbcService;
    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into team_project_application (
            project_application_id
        ) values (?);
        """;

    public void batchUpdate(final List<ProjectApplication> applications) {
        final List<TeamProjectApplication> teams = applications.stream()
            .filter(TeamProjectApplication.class::isInstance)
            .map(TeamProjectApplication.class::cast)
            .toList();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final TeamProjectApplication application = teams.get(i);
                ps.setLong(1, application.getId().getValue());
            }

            @Override
            public int getBatchSize() {
                return teams.size();
            }
        });

        teammateJdbcService.batchUpdate(teams);
    }
}
