package com.gmail.bishoybasily.cdc.controller;

import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaUser;
import com.gmail.bishoybasily.cdc.model.mysql.reporitory.MySqlRepositoryUsers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ControllerUsers {

    private MySqlRepositoryUsers mySqlRepositoryUsers;

    public ControllerUsers(MySqlRepositoryUsers mySqlRepositoryUsers) {
        this.mySqlRepositoryUsers = mySqlRepositoryUsers;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<JpaUser> all() {
        return mySqlRepositoryUsers.findAll();
    }

}
