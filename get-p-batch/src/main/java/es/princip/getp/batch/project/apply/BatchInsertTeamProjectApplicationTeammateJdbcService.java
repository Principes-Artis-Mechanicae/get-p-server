package es.princip.getp.batch.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.domain.project.apply.model.Teammate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertTeamProjectApplicationTeammateJdbcService {

    private record ProjectApplicationIdTeammate(
        ProjectApplicationId applicationId,
        Teammate teammate
    ) {
    }

    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into team_project_application_teammate (
           teammate_id,
           people_id,
           status,
           project_application_id
        ) values (?, ?, ?, ?)
        """;

    public void batchUpdate(final List<TeamProjectApplication> applications) {
        final List<ProjectApplicationIdTeammate> teammates = applications.stream()
            .flatMap(application -> application.getTeammates().stream()
                .map(teammate -> new ProjectApplicationIdTeammate(application.getId(), teammate)))
            .toList();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final ProjectApplicationId applicationId = teammates.get(i).applicationId();
                final Teammate teammate = teammates.get(i).teammate();
                ps.setLong(1, teammate.getId().getValue());
                ps.setLong(2, teammate.getPeopleId().getValue());
                ps.setString(3, teammate.getStatus().toString());
                ps.setLong(4, applicationId.getValue());
            }

            @Override
            public int getBatchSize() {
                return teammates.size();
            }
        });
    }
}
