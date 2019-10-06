package com.gmail.bishoybasily.cdc.model.mysql.reporitory;

import com.gmail.bishoybasily.cdc.model.mysql.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlRepositoryUsers extends JpaRepository<JpaUser, String> {

}
