package com.gmail.bishoybasily.cdc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Runr implements CommandLineRunner {

    private RepositoryUsers repositoryUsers;

    public Runr(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void run(String... args) {
        List<User> users = Stream.of("usr1", "usr2")
                .map(User::new)
                .collect(Collectors.toList());
        repositoryUsers.saveAll(users);
    }

}
