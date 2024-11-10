package es.princip.getp.batch.parallel;

import es.princip.getp.batch.BatchInsertionException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ParallelBatchInsertService {

    @Getter
    private final int numThreads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

    public void insert(final int size, final ParallelBatchInserter batchInserter) {
        final List<CompletableFuture<Void>> futures = new ArrayList<>();
        final int batchSize = size / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int start = i * batchSize + 1;
            final int end = (i == numThreads - 1) ? size : start + batchSize - 1;
            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                final String threadName = Thread.currentThread().getName();
                log.info("Thread {} is inserting projects from {} to {}", threadName, start, end);
                try {
                    batchInserter.insert(start, end);
                } catch (final Exception exception) {
                    throw new BatchInsertionException(
                        String.format(
                                "Thread %s encountered an error during batch insert for range %d to %d: ",
                                threadName,
                                start,
                                end
                        ),
                        exception
                    );
                }
            }, executorService);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        log.info("All threads completed. Executor service shutdown.");
    }
}
