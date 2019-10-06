package com.gmail.bishoybasily.cdc.model.elasticsearch.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class AbstractElasticSearchEntity {

    @Id
    protected String id;

}
