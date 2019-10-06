package com.gmail.bishoybasily.cdc.model.mysql.entity;

import com.gmail.bishoybasily.cdc.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = Constants.Entities.ACCOUNTS)
public class JpaAccount extends AbstractJpaEntity {

    private Type type;
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private JpaUser user;

    public enum Type {
        DEBIT,
        CREDIT
    }

}
