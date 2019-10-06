package com.gmail.bishoybasily.cdc.model.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmail.bishoybasily.cdc.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = Constants.Entities.USERS)
public class JpaUser extends AbstractJpaEntity {

    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<JpaAccount> accounts = new HashSet<>();

    public JpaUser() {
    }

    public JpaUser(String name) {
        this.name = name;
    }

}
