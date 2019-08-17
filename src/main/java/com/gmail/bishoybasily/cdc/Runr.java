package com.gmail.bishoybasily.cdc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        repositoryUsers.saveAll(Stream.of("usr1", "usr2").map(it -> new User().setName(it)).collect(Collectors.toList()));
    }

}
