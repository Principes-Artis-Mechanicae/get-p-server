package es.princip.getp.persistence.support;

public interface DataLoader {
    void load(int size);

    void teardown();
}
