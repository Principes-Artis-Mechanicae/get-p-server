package es.princip.getp.batch.project.commission;

import es.princip.getp.domain.common.model.AttachmentFile;
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
class BatchInsertProjectAttachmentFileJdbcService {

    private record ProjectIdAttachmentFile(
        ProjectId projectId,
        AttachmentFile attachmentFile
    ) {
    }

    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into project_attachment_file (project_id, attachment_files)
        values (?, ?);
        """;

    public void batchUpdate(final List<Project> projects) {
        final List<ProjectIdAttachmentFile> attachmentFiles = projects.stream()
            .flatMap(project -> project.getAttachmentFiles().stream()
                    .map(file -> new ProjectIdAttachmentFile(project.getId(), file)))
            .toList();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final Long projectId = attachmentFiles.get(i).projectId().getValue();
                final String attachmentFileUrl = attachmentFiles.get(i).attachmentFile().getUrl().getValue();
                ps.setLong(1, projectId);
                ps.setString(2, attachmentFileUrl);
            }

            @Override
            public int getBatchSize() {
                return attachmentFiles.size();
            }
        });
    }
}
