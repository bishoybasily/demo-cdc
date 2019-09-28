package com.gmail.bishoybasily.cdc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ControllerUsers {

    private RepositoryUsers repositoryUsers;

    public ControllerUsers(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @GetMapping
    public Iterable<User> all() {
        return repositoryUsers.findAll();
    }

}
