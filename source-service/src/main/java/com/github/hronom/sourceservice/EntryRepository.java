package com.github.hronom.sourceservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntryRepository extends MongoRepository<Entry, String> {
}
