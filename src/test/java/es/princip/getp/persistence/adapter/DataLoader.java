package es.princip.getp.persistence.adapter;

public interface DataLoader {
    void load(int size);

    void teardown();
}
