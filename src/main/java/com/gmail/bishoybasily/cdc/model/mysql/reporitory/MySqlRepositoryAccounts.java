package com.gmail.bishoybasily.cdc.model.mysql.reporitory;

import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlRepositoryAccounts extends JpaRepository<JpaAccount, String> {

}
