package com.gmail.bishoybasily.cdc.controller;

import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaAccount;
import com.gmail.bishoybasily.cdc.model.mysql.reporitory.MySqlRepositoryAccounts;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class ControllerAccounts {

    private MySqlRepositoryAccounts mySqlRepositoryAccounts;

    public ControllerAccounts(MySqlRepositoryAccounts mySqlRepositoryAccounts) {
        this.mySqlRepositoryAccounts = mySqlRepositoryAccounts;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<JpaAccount> all() {
        return mySqlRepositoryAccounts.findAll();
    }

}
