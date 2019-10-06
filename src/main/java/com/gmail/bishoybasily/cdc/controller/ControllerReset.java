package com.gmail.bishoybasily.cdc.controller;

import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaAccount;
import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaUser;
import com.gmail.bishoybasily.cdc.model.mysql.reporitory.MySqlRepositoryAccounts;
import com.gmail.bishoybasily.cdc.model.mysql.reporitory.MySqlRepositoryUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class ControllerReset {

    private final MySqlRepositoryAccounts mySqlRepositoryAccounts;
    private final MySqlRepositoryUsers mySqlRepositoryUsers;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String reset() {

        mySqlRepositoryAccounts.findAll().forEach(new Consumer<JpaAccount>() {
            @Override
            public void accept(JpaAccount account) {
                mySqlRepositoryAccounts.delete(account);
            }
        });
        mySqlRepositoryUsers.findAll().forEach(new Consumer<JpaUser>() {
            @Override
            public void accept(JpaUser user) {
                mySqlRepositoryUsers.delete(user);
            }
        });

        Stream.of("usr1", "usr2")
                .map(JpaUser::new)
                .forEach(new Consumer<JpaUser>() {
                    @Override
                    public void accept(JpaUser user) {
                        mySqlRepositoryUsers.save(user);
                    }
                });

        mySqlRepositoryUsers.findAll().stream()
                .flatMap(user -> {
                    return Stream.of(JpaAccount.Type.DEBIT, JpaAccount.Type.CREDIT)
                            .map(type -> new JpaAccount().setType(type).setUser(user).setBalance(0.0));
                })
                .forEach(new Consumer<JpaAccount>() {
                    @Override
                    public void accept(JpaAccount account) {
                        mySqlRepositoryAccounts.save(account);
                    }
                });

        return "Done";

    }

}
