package es.princip.getp.application.project.apply.port.out;

public interface CheckProjectApplicationPort {

    boolean existsBy(Long applicantId, Long projectId);
}
