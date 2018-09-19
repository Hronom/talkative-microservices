package com.github.hronom.sourceservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.stream.Stream;

public interface EntryRepository extends MongoRepository<Entry, String> {
    @Query("{}")
    Stream<Entry> findAllByCustomQueryAndStream();
}
