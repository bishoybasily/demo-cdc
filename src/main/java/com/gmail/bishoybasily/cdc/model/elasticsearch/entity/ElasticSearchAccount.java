package com.gmail.bishoybasily.cdc.model.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class ElasticSearchAccount {

    private String id;
    private Type type;
    private Double balance;
    @JsonSetter("user_id")
    private String userId;

    public enum Type {
        DEBIT,
        CREDIT
    }

}
