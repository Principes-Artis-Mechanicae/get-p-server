package es.princip.getp.infra;

public interface DataLoader {
    void load(int size);

    void teardown();
}
