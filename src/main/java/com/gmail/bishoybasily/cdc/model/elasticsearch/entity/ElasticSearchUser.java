package com.gmail.bishoybasily.cdc.model.elasticsearch.entity;

import com.gmail.bishoybasily.cdc.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Document(indexName = Constants.Entities.USERS)
public class ElasticSearchUser extends AbstractElasticSearchEntity {

    private String name;

    private Set<ElasticSearchAccount> accounts = new HashSet<>();

    public ElasticSearchUser() {
    }

    public ElasticSearchUser(String name) {
        this.name = name;
    }

}
