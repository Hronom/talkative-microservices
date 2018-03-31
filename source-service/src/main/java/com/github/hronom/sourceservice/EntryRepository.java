package com.github.hronom.sourceservice;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntryRepository extends PagingAndSortingRepository<Entry, String> {
}
