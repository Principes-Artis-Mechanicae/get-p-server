package es.princip.getp.application.project.apply.port.out;

public interface CheckProjectApplicationPort {

    boolean existsByApplicantIdAndProjectId(Long applicantId, Long projectId);
}