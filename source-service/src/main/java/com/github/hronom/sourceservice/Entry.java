package com.github.hronom.sourceservice;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "key", def = "{'key': 'hashed'}"),
    @CompoundIndex(name = "shard_key", def = "{'_id': 'hashed'}")
})
public class Entry {
    @Id
    public String id;

    public String key;

    public String text;

    @Version
    public Long version;
    @CreatedDate
    public Date createdDate;
    @LastModifiedDate
    public Date lastModifiedDate;
}