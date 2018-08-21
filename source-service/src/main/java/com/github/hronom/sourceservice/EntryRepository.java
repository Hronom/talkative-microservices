package com.github.hronom.sourceservice;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.stream.Stream;

public interface EntryRepository extends PagingAndSortingRepository<Entry, String> {
    @Query("{}")
    Stream<Entry> findAllByCustomQueryAndStream();
}
