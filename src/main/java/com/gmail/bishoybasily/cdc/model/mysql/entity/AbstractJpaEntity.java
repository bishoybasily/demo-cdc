package com.gmail.bishoybasily.cdc.model.mysql.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@MappedSuperclass
public class AbstractJpaEntity {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.gmail.bishoybasily.cdc.model.mysql.generator.IdGenerator")
    protected String id;

}
