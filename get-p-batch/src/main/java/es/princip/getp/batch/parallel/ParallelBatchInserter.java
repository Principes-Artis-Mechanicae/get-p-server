package es.princip.getp.batch.parallel;

@FunctionalInterface
public interface ParallelBatchInserter {
    void insert(int start, int end);
}
