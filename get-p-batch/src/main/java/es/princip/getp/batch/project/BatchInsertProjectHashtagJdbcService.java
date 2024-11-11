package es.princip.getp.batch.project;

import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertProjectHashtagJdbcService {

    private record ProjectIdHashtag(
        ProjectId projectId,
        Hashtag hashtag
    ) {
    }

    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into project_hashtag (project_id, hashtags)
        values (?, ?)
        """;

    public void batchUpdate(final List<Project> projects) {
        final List<ProjectIdHashtag> hashtags = projects.stream()
            .flatMap(project -> project.getHashtags().stream()
                .map(hashtag -> new ProjectIdHashtag(project.getId(), hashtag)))
            .toList();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final Long projectId = hashtags.get(i).projectId().getValue();
                final String hashtag = hashtags.get(i).hashtag().getValue();
                ps.setLong(1, projectId);
                ps.setString(2, hashtag);
            }

            @Override
            public int getBatchSize() {
                return hashtags.size();
            }
        });
    }
}
