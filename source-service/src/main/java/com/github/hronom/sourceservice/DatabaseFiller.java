package com.github.hronom.sourceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.UUID;

@Service
@Profile("refill-db")
public class DatabaseFiller implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntryRepository entryRepository;

    @Autowired
    public DatabaseFiller(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Cleaning database...");
        entryRepository.deleteAll();
        long begin = System.currentTimeMillis();
        logger.info("Inserting test entries in database...");
        LinkedList<Entry> entries = new LinkedList<>();
        for (int i = 0; i < 1_000_000; i++) {
            Entry entry = new Entry();
            entry.id = "id " + i;
            entry.key = String.valueOf(i % 3);
            entry.text = generateString(1500);
            entries.add(entry);
            if (entries.size() > 100_000) {
                entryRepository.saveAll(entries);
                entries.clear();
            }
        }
        if (!entries.isEmpty()) {
            entryRepository.saveAll(entries);
            entries.clear();
        }
        long end = System.currentTimeMillis();
        logger.info("Done inserting of test entries in database. Took " + (end - begin) + " ms.");
    }

    private String generateString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < length) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append(UUID.randomUUID().toString());
        }
        return stringBuilder.toString().substring(0, length);
    }
}
