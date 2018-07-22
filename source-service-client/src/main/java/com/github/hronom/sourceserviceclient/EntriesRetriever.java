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
        getWithSharding(1, 40);
    }

    private void getWithSharding(int currentShardNumber, int totalShards) {
        long pageSize = 1000L; // Is the max.
        String sort = "createdDate:asc";
        PagedResources<Entry> pagedResources =
            sourceServiceClient.getStores(0, pageSize, sort);
        if(pagedResources.getContent().size() != pageSize){
            throw new IllegalStateException(
                String.format(
                    "Count of returned item was %d, but requested page size %d",
                    pagedResources.getContent().size(),
                    pageSize
                )
            );
        }
        long totalPages = pagedResources.getMetadata().getTotalPages();

        for (int i = 1; i < Runtime.getRuntime().availableProcessors() + 1; i++) {
            long begin = System.currentTimeMillis();

            getMultiThread(pageSize, totalPages, i, currentShardNumber, totalShards, sort);

            long end = System.currentTimeMillis();
            logger.info(String.format(
                "Done, threads: %d, page size: %d, total pages %d, current shard number %d, total shards %d, time: %d ms.",
                i,
                pageSize,
                totalPages,
                currentShardNumber,
                totalShards,
                (end - begin)
            ));
        }

        logger.info("Test done.");

        // Results:

        // Spring Boot 2.0.2
        // Total shard 10
        // Done, threads: 1, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 30407 ms.
        // Done, threads: 2, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 16044 ms.
        // Done, threads: 3, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 11445 ms.
        // Done, threads: 4, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 9558 ms.
        // Done, threads: 5, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 8847 ms.
        // Done, threads: 6, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 9181 ms.
        // Done, threads: 7, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 9626 ms.
        // Done, threads: 8, page size: 1000, total pages 1000, current shard number 1, total shards 10, time: 9346 ms.

        // Total shard 20
        // Done, threads: 1, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 15079 ms.
        // Done, threads: 2, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 8074 ms.
        // Done, threads: 3, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 5952 ms.
        // Done, threads: 4, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 4860 ms.
        // Done, threads: 5, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 4754 ms.
        // Done, threads: 6, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 4360 ms.
        // Done, threads: 7, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 4579 ms.
        // Done, threads: 8, page size: 1000, total pages 1000, current shard number 1, total shards 20, time: 4552 ms.

        // Total shard 30
        // Done, threads: 1, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 10395 ms.
        // Done, threads: 2, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 5542 ms.
        // Done, threads: 3, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 4058 ms.
        // Done, threads: 4, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 3495 ms.
        // Done, threads: 5, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 3065 ms.
        // Done, threads: 6, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 2820 ms.
        // Done, threads: 7, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 3122 ms.
        // Done, threads: 8, page size: 1000, total pages 1000, current shard number 1, total shards 30, time: 3011 ms.

        // Total shard 40
        // Done, threads: 1, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 7559 ms.
        // Done, threads: 2, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 4109 ms.
        // Done, threads: 3, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2892 ms.
        // Done, threads: 4, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2406 ms.
        // Done, threads: 5, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2239 ms.
        // Done, threads: 6, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2186 ms.
        // Done, threads: 7, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2067 ms.
        // Done, threads: 8, page size: 1000, total pages 1000, current shard number 1, total shards 40, time: 2087 ms.
    }

    private void getMultiThread(
        long pageSize,
        long totalPages,
        int parallelism,
        int currentShardNumber,
        int totalShards,
        String sort
    ) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
        LinkedList<ForkJoinTask> tasks = new LinkedList<>();
        for (int i = 0; i < totalPages; i++) {
            int finalI = i;
            if (finalI % totalShards == currentShardNumber) {
                tasks.add(forkJoinPool.submit(() -> {
                    sourceServiceClient.getStores(finalI, pageSize, sort);
                }));
            }
        }

        for (ForkJoinTask task : tasks) {
            task.join();
        }
    }
}
