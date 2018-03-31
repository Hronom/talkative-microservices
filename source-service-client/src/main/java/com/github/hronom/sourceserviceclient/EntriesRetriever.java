package com.github.hronom.sourceserviceclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Service
public class EntriesRetriever implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SourceServiceClient sourceServiceClient;

    @Autowired
    public EntriesRetriever(SourceServiceClient sourceServiceClient) {
        this.sourceServiceClient = sourceServiceClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long pageSize = 1000L;
        PagedResources<Entry> pagedResources = sourceServiceClient.getStores(0, pageSize);
        long totalPages = pagedResources.getMetadata().getTotalPages();

        //getMultiThread(1000L, totalPages, 8);
        for (int i = 1; i < Runtime.getRuntime().availableProcessors() + 1; i++) {
            long begin = System.currentTimeMillis();

            getMultiThread(1000L, totalPages, i);

            long end = System.currentTimeMillis();
            logger.info(String.format(
                "Done, threads: %d, page size: %d, total pages %d, time: %d ms.",
                i,
                pageSize,
                totalPages,
                (end - begin)
            ));
        }

        // Results
        // Done, threads: 1, page size: 1000, total pages 1000, time: 331595 ms.
        // Done, threads: 2, page size: 1000, total pages 1000, time: 192680 ms.
        // Done, threads: 3, page size: 1000, total pages 1000, time: 138867 ms.
        // Done, threads: 4, page size: 1000, total pages 1000, time: 122536 ms.
        // Done, threads: 5, page size: 1000, total pages 1000, time: 114898 ms.
        // Done, threads: 6, page size: 1000, total pages 1000, time: 113297 ms.
        // Done, threads: 7, page size: 1000, total pages 1000, time: 104802 ms.
        // Done, threads: 8, page size: 1000, total pages 1000, time: 108093 ms.
    }

    private void getSingleThread(long pageSize, long totalPages) {
        for (int i = 0; i < totalPages; i++) {
            PagedResources<Entry> pagedResources = sourceServiceClient.getStores(i, pageSize);
        }
    }

    private void getMultiThread(long pageSize, long totalPages, int parallelism) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
        LinkedList<ForkJoinTask> tasks = new LinkedList<>();
        for (int i = 0; i < totalPages; i++) {
            int finalI = i;
            tasks.add(
                forkJoinPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        PagedResources<Entry> pagedResources =
                            sourceServiceClient.getStores(finalI, pageSize);
                    }
                })
            );
        }

        for (ForkJoinTask task : tasks) {
            task.join();
        }
    }
}
