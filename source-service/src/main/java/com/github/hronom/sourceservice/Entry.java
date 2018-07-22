package com.github.hronom.sourceservice;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Entry {
    @Id
    public String id;

    @Indexed
    public String key;

    public String text;

    @Version
    public Long version;
    @CreatedDate
    public Date createdDate;
    @LastModifiedDate
    public Date lastModifiedDate;
}