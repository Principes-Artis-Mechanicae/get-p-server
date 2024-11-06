package es.princip.getp.domain.project.apply.model;

public enum ProjectApplicationType {
    INDIVIDUAL, // 개인
    TEAM; // 팀

    public boolean isIndividual() {
        return this == INDIVIDUAL;
    }

    public boolean isTeam() {
        return this == TEAM;
    }
}
