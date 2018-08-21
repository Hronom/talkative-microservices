package com.github.hronom.sourceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.Stream;

@Service
@Profile("iterate-over-all")
public class IterateOverAllEntities implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntryRepository entryRepository;

    @Autowired
    public IterateOverAllEntities(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Stream<Entry> entries = entryRepository.findAllByCustomQueryAndStream()) {
            Iterator<Entry> iter = entries.iterator();
            while (iter.hasNext()) {
                Entry entry = iter.next();
                System.out.println(entry.id);
            }
        }
    }
}
